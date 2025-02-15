package com.wxj.mapper;

import com.wxj.entity.Checks;

import java.util.List;

public interface CheckMapper {

    /**
      * 新增
    */
    int insert(Checks checks);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Checks checks);

    /**
      * 根据ID查询
    */
    Checks selectById(Integer id);

    /**
      * 查询所有
    */
    List<Checks> selectAll(Checks checks);

}