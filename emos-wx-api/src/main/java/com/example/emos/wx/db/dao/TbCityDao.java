package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbCity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 疫情城市列表(TbCity)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public interface TbCityDao {

    /**
     * 查询城市对应编码
     */
    String searchCode(String city);

}

