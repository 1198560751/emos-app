package com.example.emos.wx.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.example.emos.wx.config.SystemConstants;
import com.example.emos.wx.db.dao.*;
import com.example.emos.wx.db.pojo.TbCheckin;
import com.example.emos.wx.exception.EmosException;
import com.example.emos.wx.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
@Scope("prototype")
@Slf4j
public class CheckServiceImpl implements CheckinService {
    @Autowired
    private SystemConstants constants;
    @Autowired
    private TbHolidaysDao tbHolidaysDao;
    @Autowired
    private TbWorkdayDao tbWorkdayDao;
    @Autowired
    private TbCheckinDao tbCheckinDao;

    @Autowired
    private TbCityDao tbCityDao;


    @Autowired
    private TbFaceModelDao tbFaceModelDao;

    @Value("${emos.face.createFaceModelUrl}")
    private String createFaceModelUrl;

    @Value("${emos.face.checkinUrl}")
    private String checkinUrl;


    /**
     * 判断用户是否可以考勤
     * 1. 节假日无需签到,工作日才签到
     * 2. 查询是否有特殊法定节假日
     */
    @Override
    public String validCanCheckIn(int userId, String date) {
        boolean boolOne = tbHolidaysDao.searchTodayIsHolidays() != null;
        boolean boolTwo = tbWorkdayDao.searchTodayIsWorkday() != null;
        String type = "工作日";
        if (DateUtil.date().isWeekend()) { //判断当前是不是周末
            type = "节假日";
        }
        if (boolOne) {
            type = "节假日";
        } else if (boolTwo) {
            type = "工作日";
        }
        if ("节假日".equals(type)) {
            return "节假日不需要考勤";
        } else {
            DateTime now = DateUtil.date();
            String start = DateUtil.today() + " " + constants.getAttendanceStartTime(); // 获取当前天拼接考勤时间
            String end = DateUtil.today() + " " + constants.getAttendanceEndTime(); // 获取当前天拼接考勤时间
            DateTime startDate = DateUtil.parse(start);
            DateTime endDate = DateUtil.parse(end);
            if (now.isBefore(startDate)) { //判断是不是在开始考勤时间之前
                return "没到上班考勤开始时间";
            } else if (now.isAfter(endDate)) {
                return "超过了上班考勤结束时间";
            } else {
                HashMap<Object, Object> map = new HashMap<>();
                map.put("userId", userId);
                map.put("date", date);
                map.put("start", start);
                map.put("end", end);
                //判断用户是否已经签到
                return tbCheckinDao.haveCheckin(map) != null ? "今日已经考勤,无需重复考勤" : "可以考勤";
            }
        }
    }

    /**
     * 执行签到
     */
    @Override
    public void checkin(HashMap<Object, Object> param) {
        Date d1 = DateUtil.date(); //获取当前时间
        Date d2 = DateUtil.parse(DateUtil.today() + "" + constants.attendanceTime);  //获取上班考勤时间
        Date d3 = DateUtil.parse(DateUtil.today() + "" + constants.attendanceEndTime);  //获取考勤结束时间
        int status = 1; // 正常考勤   1.正常考勤,2.迟到,3.矿工
        if (d1.compareTo(d2) <= 0) {     //正常考勤

        } else if (d1.compareTo(d2) > 0 && d1.compareTo(d3) < 0) {  //过了考勤开始但是没有过考勤结束 迟到
            status = 2;
        }
        int userId = (Integer) param.get("userId");  //获取传过来的userId
        //查询人脸模型数据
        String faceModel = tbFaceModelDao.searchFaceModel(userId);
        if (faceModel == null) {
            throw new EmosException("不存在人脸模型");
        }
        //比较人脸识别
        String path = (String) param.get("path");
        // TODO: 发送Http请求到python程序校验人脸识别,后期换成腾讯云
        HttpRequest request = HttpUtil.createPost(checkinUrl);
        request.form("photo", FileUtil.file(path), "targetModel", faceModel);  //找到图片并且上传
        HttpResponse response = request.execute();
        if (response.getStatus() != 200) {
            log.error("人脸识别服务异常");
            throw new EmosException("人脸识别服务异常");
        }
        String body = response.body();  //获取响应体
        if ("无法识别出人脸".equals(body) || "照片中存在多张人脸".equals(body)) {
            throw new EmosException(body);
        }
        if ("False".equals(body)) {
            throw new EmosException("签到无效,非本人签到");
        }
        if ("True".equals(body)) {    //查询疫情风险等级
            int risk = 1;   //风险等级  1.低风险,2.中风险,3.高风险
            String city = (String) param.get("city");  //城市
            String district = (String) param.get("district"); //区县
            if (!StrUtil.isAllBlank(city, district)) {
                String code = tbCityDao.searchCode(city);   //查询城市对应编码
                if (code == null) {
                    log.error("查询城市编码错误");
                    throw new EmosException("查询城市编码错误");
                }
                String url = "http://m." + code + ".bendibao.com/news/yqdengji/?qu=" + district;
                try {
                    Document document = Jsoup.connect(url).get();    // 通过Jsoup发送请求
                    Elements elements = document.getElementsByClass("list-content");
                    if (elements.size() > 0) {
                        Element element = elements.get(0);  //获取第一个元素
                        String result = element.select("P:last-child").text();  // 获取最后一个元素的文字
                        if ("高风险".equals(result)) {
                            // 发送告警邮件
                            risk = 3;
                        } else if ("中风险".equals(result)) {
                            risk = 2;
                        }
                    }
                } catch (Exception e) {
                    log.error("执行异常", e);
                    throw new EmosException("获取风险等级失败");
                }
            }
            //保存签到记录
            TbCheckin entity = new TbCheckin();
            entity.setUserId(userId);
            entity.setAddress((String) param.get("address"));
            entity.setCountry((String) param.get("country"));
            entity.setProvince((String) param.get("province"));
            entity.setCity(city);
            entity.setDistrict(district);
            entity.setStatus((byte) status);
            entity.setDate(DateUtil.today());
            entity.setCreateTime(d1);
            tbCheckinDao.insert(entity);
        }


    }
}
