package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbHolidays;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 节假日表(TbHolidays)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public interface TbHolidaysDao {

    Integer searchTodayIsHolidays();

}

