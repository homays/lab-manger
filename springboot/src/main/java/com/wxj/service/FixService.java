package com.wxj.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxj.common.enums.RoleEnum;
import com.wxj.entity.Account;
import com.wxj.entity.Fix;
import com.wxj.entity.Lab;
import com.wxj.entity.Type;
import com.wxj.mapper.FixMapper;
import com.wxj.mapper.LabMapper;
import com.wxj.mapper.TypeMapper;
import com.wxj.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FixService {

    @Resource
    private FixMapper fixMapper;
    @Resource
    private LabMapper labMapper;
    @Resource
    private TypeMapper typeMapper;

    /**
     * 新增
     */
    public void add(Fix fix) {
        fix.setTime(DateUtil.now());
        Lab lab = labMapper.selectById(fix.getLabId());
        if (ObjectUtil.isNotEmpty(lab) && ObjectUtil.isNotEmpty(lab.getTypeId())) {
            Type type = typeMapper.selectById(lab.getTypeId());
            if (ObjectUtil.isNotEmpty(type)) {
                fix.setTypeId(type.getId());
            }
        }
        fixMapper.insert(fix);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        fixMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            fixMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Fix fix) {
        fixMapper.updateById(fix);
    }

    /**
     * 根据ID查询
     */
    public Fix selectById(Integer id) {
        return fixMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Fix> selectAll(Fix fix) {
        return fixMapper.selectAll(fix);
    }

    /**
     * 分页查询
     */
    public PageInfo<Fix> selectPage(Fix fix, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.STUDENT.name().equals(currentUser.getRole())) {
            fix.setStudentId(currentUser.getId());
        }
        if (RoleEnum.LABADMIN.name().equals(currentUser.getRole())) {
            fix.setLabadminId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Fix> list = fixMapper.selectAll(fix);
        return PageInfo.of(list);
    }

}