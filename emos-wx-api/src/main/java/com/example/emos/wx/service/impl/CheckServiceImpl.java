package com.example.emos.wx.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.emos.wx.config.SystemConstants;
import com.example.emos.wx.db.dao.TbCheckinDao;
import com.example.emos.wx.db.dao.TbHolidaysDao;
import com.example.emos.wx.db.dao.TbWorkdayDao;
import com.example.emos.wx.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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


    /**
     * 判断用户是否可以考勤
     * 1. 节假日无需签到,工作日才签到
     * 2. 查询是否有特殊法定节假日
     * @return
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
}
