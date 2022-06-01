package com.example.emos.wx.service;

import java.util.HashMap;

public interface CheckinService {
    /**
     * 检查当前是否已经签到
     */
    String validCanCheckIn(int userId, String date);

    /**
     * 检查
     */
    void checkin(HashMap<Object, Object> param);
}
