package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbWorkday;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TbWorkday)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-18 23:21:33
 */
public interface TbWorkdayDao {
    Integer searchTodayIsWorkday();
}

