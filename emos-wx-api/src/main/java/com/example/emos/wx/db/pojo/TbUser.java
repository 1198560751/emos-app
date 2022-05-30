package com.example.emos.wx.db.pojo;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(TbUser)实体类
 *
 * @author makejava
 * @since 2022-05-18 23:21:33
 */
@Data
public class TbUser implements Serializable {
    private static final long serialVersionUID = 972448931743173228L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 长期授权字符串
     */
    private String openId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像网址
     */
    private String photo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Object sex;
    /**
     * 手机号码
     */
    private String tel;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 入职日期
     */
    private Date hiredate;
    /**
     * 角色
     */
    private Object role;
    /**
     * 是否是超级管理员
     */
    private Integer root;
    /**
     * 部门编号
     */
    private Integer deptId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
}

