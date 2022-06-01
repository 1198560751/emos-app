package com.example.emos.wx.db.pojo;

import java.util.Date;
import java.io.Serializable;

/**
 * 签到表(TbCheckin)实体类
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public class TbCheckin implements Serializable {
    private static final long serialVersionUID = -43333297251442190L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 签到地址
     */
    private String address;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区划
     */
    private String district;
    /**
     * 考勤结果
     */
    private Byte status;
    /**
     * 风险等级
     */
    private Integer risk;
    /**
     * 签到日期
     */
    private String date;
    /**
     * 签到时间
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getRisk() {
        return risk;
    }

    public void setRisk(Integer risk) {
        this.risk = risk;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

