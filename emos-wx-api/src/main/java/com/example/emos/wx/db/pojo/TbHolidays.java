package com.example.emos.wx.db.pojo;

import java.util.Date;
import java.io.Serializable;

/**
 * 节假日表(TbHolidays)实体类
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public class TbHolidays implements Serializable {
    private static final long serialVersionUID = -27465517671965088L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 日期
     */
    private Date date;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

