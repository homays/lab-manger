package com.wxj.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxj.common.enums.ResultCodeEnum;
import com.wxj.entity.Type;
import com.wxj.exception.CustomException;
import com.wxj.mapper.TypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeService {

    @Resource
    private TypeMapper typeMapper;

    /**
     * 新增
     */
    public void add(Type type) {
        Type dbType = typeMapper.selectByName(type);
        if (ObjectUtil.isNotNull(dbType)) {
            throw new CustomException(ResultCodeEnum.TYPE_EXIST_ERROR);
        }
        typeMapper.insert(type);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        typeMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            typeMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Type type) {
        typeMapper.updateById(type);
    }

    /**
     * 根据ID查询
     */
    public Type selectById(Integer id) {
        return typeMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Type> selectAll(Type type) {
        return typeMapper.selectAll(type);
    }

    /**
     * 分页查询
     */
    public PageInfo<Type> selectPage(Type type, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Type> list = typeMapper.selectAll(type);
        return PageInfo.of(list);
    }

}