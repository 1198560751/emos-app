package com.example.emos.wx.db.pojo;

import java.io.Serializable;

/**
 * 疫情城市列表(TbCity)实体类
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public class TbCity implements Serializable {
    private static final long serialVersionUID = -66112356237932729L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 城市名称
     */
    private String city;
    /**
     * 拼音简称
     */
    private String code;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

