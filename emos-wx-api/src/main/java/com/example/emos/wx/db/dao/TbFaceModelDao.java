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
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbFaceModel queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param tbFaceModel 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TbFaceModel> queryAllByLimit(TbFaceModel tbFaceModel, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param tbFaceModel 查询条件
     * @return 总行数
     */
    long count(TbFaceModel tbFaceModel);

    /**
     * 新增数据
     *
     * @param tbFaceModel 实例对象
     * @return 影响行数
     */
    int insert(TbFaceModel tbFaceModel);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbFaceModel> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TbFaceModel> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbFaceModel> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TbFaceModel> entities);

    /**
     * 修改数据
     *
     * @param tbFaceModel 实例对象
     * @return 影响行数
     */
    int update(TbFaceModel tbFaceModel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

