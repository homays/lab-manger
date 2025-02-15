package com.wxj.mapper;

import com.wxj.entity.Lab;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LabMapper {

    /**
      * 新增
    */
    int insert(Lab lab);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Lab lab);

    /**
      * 根据ID查询
    */
    Lab selectById(Integer id);

    /**
      * 查询所有
    */
    List<Lab> selectAll(Lab lab);

    @Select("select count(*) from lab where status = #{status}")
    long selectCount(String status);

    @Select("select * from lab where type_id = #{typeId}")
    List<Lab> selectByTypeId(Integer typeId);
}