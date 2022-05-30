package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 用户表(TbUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-18 23:21:33
 */
@Mapper
public interface TbUserDao {

    /**
     * 查询是否有超级管理员
     */
    boolean haveRootUser();

    /**
     * 插入用户信息
     *
     * @param param 用户信息
     * @return 影响行数
     */
    int insert(HashMap<Object,Object> param);

    /**
     * 通过OpenId 查询用户信息
     */
    Integer searchIdByOpenId(String openId);

    /**
     * 查询用户权限
     */
    Set<String> searchUserPermissions(int userId);

    /**
     * 查询用户信息
     */
    TbUser searchById(int UserId);
}

