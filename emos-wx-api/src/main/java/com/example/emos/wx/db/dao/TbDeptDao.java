package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TbDept)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-18 23:21:32
 */
public interface TbDeptDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbDept queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param tbDept 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TbDept> queryAllByLimit(TbDept tbDept, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param tbDept 查询条件
     * @return 总行数
     */
    long count(TbDept tbDept);

    /**
     * 新增数据
     *
     * @param tbDept 实例对象
     * @return 影响行数
     */
    int insert(TbDept tbDept);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbDept> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbDept> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbDept> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TbDept> entities);

    /**
     * 修改数据
     *
     * @param tbDept 实例对象
     * @return 影响行数
     */
    int update(TbDept tbDept);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

