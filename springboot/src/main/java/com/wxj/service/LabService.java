package com.wxj.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxj.common.enums.ResultCodeEnum;
import com.wxj.common.enums.RoleEnum;
import com.wxj.common.enums.StatusEnum;
import com.wxj.entity.Account;
import com.wxj.entity.Lab;
import com.wxj.entity.Type;
import com.wxj.exception.CustomException;
import com.wxj.mapper.LabMapper;
import com.wxj.mapper.TypeMapper;
import com.wxj.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabService {

    @Resource
    private LabMapper labMapper;
    @Resource
    private TypeMapper typeMapper;

    /**
     * 新增
     */
    public void add(Lab lab) {
        // 查询一下该实验室分类的是实验室管理员信息
        Type type = typeMapper.selectById(lab.getTypeId());
        if (ObjectUtil.isEmpty(type)) {
            throw new CustomException(ResultCodeEnum.TYPE_NO_ERROR);
        }
        if (ObjectUtil.isEmpty(type.getLabadminId())) {
            throw new CustomException(ResultCodeEnum.TYPE_LABADMIN_NO_ERROR);
        }
        lab.setLabadminId(type.getLabadminId());
        lab.setStatus(StatusEnum.OK.getDesc());
        labMapper.insert(lab);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        labMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            labMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Lab lab) {
        labMapper.updateById(lab);
    }

    /**
     * 根据ID查询
     */
    public Lab selectById(Integer id) {
        return labMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Lab> selectAll(Lab lab) {
        return labMapper.selectAll(lab);
    }

    /**
     * 分页查询
     */
    public PageInfo<Lab> selectPage(Lab lab, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.LABADMIN.name().equals(currentUser.getRole())) {
            lab.setLabadminId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Lab> list = labMapper.selectAll(lab);
        return PageInfo.of(list);
    }

    public long selectCount(String status) {
        return labMapper.selectCount(status);
    }
}