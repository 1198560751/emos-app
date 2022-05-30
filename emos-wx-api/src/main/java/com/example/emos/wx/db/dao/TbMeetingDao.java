package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbMeeting;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 会议表(TbMeeting)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public interface TbMeetingDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbMeeting queryById(Object id);

    /**
     * 查询指定行数据
     *
     * @param tbMeeting 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TbMeeting> queryAllByLimit(TbMeeting tbMeeting, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param tbMeeting 查询条件
     * @return 总行数
     */
    long count(TbMeeting tbMeeting);

    /**
     * 新增数据
     *
     * @param tbMeeting 实例对象
     * @return 影响行数
     */
    int insert(TbMeeting tbMeeting);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMeeting> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbMeeting> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMeeting> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TbMeeting> entities);

    /**
     * 修改数据
     *
     * @param tbMeeting 实例对象
     * @return 影响行数
     */
    int update(TbMeeting tbMeeting);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Object id);

}

