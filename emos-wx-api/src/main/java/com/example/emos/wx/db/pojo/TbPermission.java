package com.example.emos.wx.db.pojo;

import java.io.Serializable;

/**
 * (TbPermission)实体类
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public class TbPermission implements Serializable {
    private static final long serialVersionUID = -29322602204760530L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 权限
     */
    private String permissionName;
    /**
     * 模块ID
     */
    private Integer moduleId;
    /**
     * 行为ID
     */
    private Integer actionId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

}

