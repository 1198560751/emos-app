package com.example.emos.wx.service;


import com.example.emos.wx.db.pojo.TbUser;

import java.util.Set;

public interface UserService {

    /**
     * @param registerCode 激活码
     * @param code 临时凭证
     * @param nickname 用户昵称
     * @param photo 用户头像
     */
    int registerUser(String registerCode, String code, String nickname, String photo);

    /**
     *  查询用户权限
     * @param userId 用户id
     */
    Set<String> searchUserPermissions(int userId);

    /**
     * 登录
     */
    Integer login(String code);

    /**
     * 查询用户信息
     */
    TbUser searchById(int UserId);
}
