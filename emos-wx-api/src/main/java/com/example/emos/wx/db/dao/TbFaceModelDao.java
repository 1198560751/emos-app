package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbFaceModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (TbFaceModel)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public interface TbFaceModelDao {

    /**
     * 通过用户Id查询人脸模型
     */
    String searchFaceModel(int userId);

    /**
     * 插入人脸模型
     */
    void insert(TbFaceModel faceModel);

    /**
     * 删除人脸模型
     */
    int deleteFaceModel(int userId);

}

