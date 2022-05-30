package com.example.emos.wx.service;

public interface CheckinService {
    /**
     * 检查当前是否已经签到
     */
    String validCanCheckIn(int userId, String date);
}
