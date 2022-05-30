package com.example.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 注册对象数据模型
 */
@Data
@ApiModel
public class RegisterForm {
    @NotBlank(message = "注册码不能为空")
    // 0-9  并且长度为6
    @Pattern(regexp = "^[0-9]{6}$", message = "注册码必须是六位数字")
    private String registerCode;

    @NotBlank(message = "微信临时授权码不能为空")
    private String code;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @NotBlank(message = "头像不能为空")
    private String ptoto;
}
