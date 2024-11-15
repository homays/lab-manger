package com.wxj.mapper;

import com.wxj.entity.Labadmin;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LabadminMapper {

    /**
      * 新增
    */
    int insert(Labadmin labadmin);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Labadmin labadmin);

    /**
      * 根据ID查询
    */
    Labadmin selectById(Integer id);

    /**
      * 查询所有
    */
    List<Labadmin> selectAll(Labadmin labadmin);

    @Select("select * from labadmin where username = #{username}")
    Labadmin selectByUsername(String username);
}