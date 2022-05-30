package com.example.emos.wx.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 初始化考勤时间封装类
 */
@Data
@Component
public class SystemConstants {
    public String attendanceStartTime;
    public String attendanceTime;
    public String attendanceEndTime;
    public String closingStartTime;
    public String closingTime;
    public String closingEndTime;

}
