package com.example.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 人脸识别  dto
 */
@Data
@ApiModel
public class CheckinForm {
    private String address;
    private String country;
    private String province;
    private String city;
    private String district;
}
