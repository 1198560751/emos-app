package com.example.emos.wx.db.pojo;

import java.time.LocalTime;
import java.util.Date;
import java.io.Serializable;

/**
 * 会议表(TbMeeting)实体类
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public class TbMeeting implements Serializable {
    private static final long serialVersionUID = 985692795509643078L;
    /**
     * 主键
     */
    private Object id;
    /**
     * UUID
     */
    private String uuid;
    /**
     * 会议题目
     */
    private String title;
    /**
     * 创建人ID
     */
    private Long creatorId;
    /**
     * 日期
     */
    private Date date;
    /**
     * 开会地点
     */
    private String place;
    /**
     * 开始时间
     */
    private LocalTime start;
    /**
     * 结束时间
     */
    private LocalTime end;
    /**
     * 会议类型（1在线会议，2线下会议）
     */
    private Integer type;
    /**
     * 参与者
     */
    private Object members;
    /**
     * 会议内容
     */
    private String desc;
    /**
     * 工作流实例ID
     */
    private String instanceId;
    /**
     * 状态（1未开始，2进行中，3已结束）
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getMembers() {
        return members;
    }

    public void setMembers(Object members) {
        this.members = members;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

