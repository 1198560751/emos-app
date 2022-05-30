package com.example.emos.wx.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.emos.wx.db.dao.TbUserDao;
import com.example.emos.wx.db.pojo.TbUser;
import com.example.emos.wx.exception.EmosException;
import com.example.emos.wx.service.UserService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl implements UserService {
    @Value("${wx.app-id}")
    private String appId;
    @Value("${wx.app-secret}")
    private String appSecret;
    @Autowired
    private TbUserDao userDao;

    /**
     * 注册
     */
    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        if ("000000".equals(registerCode)) {
            //注册超级管理员
            boolean flag = userDao.haveRootUser();
            if (!flag) {    //没有超级管理账号
                String openId = getOpenId(code);
                HashMap<Object, Object> hashMap = new HashMap<>();
                hashMap.put("openId", openId);
                hashMap.put("nickname", nickname);
                hashMap.put("photo", photo);
                hashMap.put("role", "[0]");
                hashMap.put("status", 1);
                hashMap.put("createTime", new Date());
                hashMap.put("root", true);
                int insert = userDao.insert(hashMap);
                Integer id = userDao.searchIdByOpenId(openId);  // 根据openId查询主键
                if (id != null) {
                    return id;
                }
            } else {   //普通员工注册
                throw new EmosException("无法绑定超级管理员账号");
            }
        }
        return 0;
    }

    /**
     * 获取用户权限
     */
    @Override
    public Set<String> searchUserPermissions(int userId) {
        return userDao.searchUserPermissions(userId);
    }

    /**
     * 登录
     *
     * @param code openId
     */
    @Override
    public Integer login(String code) {
        //根据传入的临时授权码获取openId,然后查库
        String openId = getOpenId(code);
        Integer id = userDao.searchIdByOpenId(openId);
        if (id == null) {
            throw new EmosException("账户不存在");
        }
        //  TODO:在消息队列中接受消息,转移到消息表   登录成功
        return id;
    }


    /**
     * 查询用户信息
     */
    @Override
    public TbUser searchById(int UserId) {
        return userDao.searchById(UserId);
    }


    /**
     * 通过临时字符串获取OpenId
     */
    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("appid", appId);
        hashMap.put("secret", appSecret);
        hashMap.put("js_code", code);
        hashMap.put("grant_type", "authorization_code");
        String result = HttpUtil.post(url, hashMap);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String openid = jsonObject.getStr("openid");
        if (StrUtil.isBlank(openid)) {
            throw new RuntimeException("临时登录凭证错误");
        }
        return openid;
    }


}
