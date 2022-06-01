package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbCheckin;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * 签到表(TbCheckin)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public interface TbCheckinDao {

    Integer haveCheckin(HashMap<Object, Object> param);

    /**
     * 插入签到记录
     */
    void insert(TbCheckin tbCheckin);

}

