package com.wxj.mapper;

import com.wxj.entity.Fix;

import java.util.List;

public interface FixMapper {

    /**
      * 新增
    */
    int insert(Fix fix);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Fix fix);

    /**
      * 根据ID查询
    */
    Fix selectById(Integer id);

    /**
      * 查询所有
    */
    List<Fix> selectAll(Fix fix);

}