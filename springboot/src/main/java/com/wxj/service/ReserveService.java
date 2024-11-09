package com.wxj.service;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxj.common.enums.ReserveEnum;
import com.wxj.common.enums.RoleEnum;
import com.wxj.common.enums.StatusEnum;
import com.wxj.entity.Account;
import com.wxj.entity.Lab;
import com.wxj.entity.Reserve;
import com.wxj.mapper.LabMapper;
import com.wxj.mapper.ReserveMapper;
import com.wxj.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReserveService {

    @Resource
    private ReserveMapper reserveMapper;
    @Resource
    private LabMapper labMapper;

    /**
     * 新增
     */
    public void add(Reserve reserve) {
        reserve.setTime(DateUtil.now());
        reserveMapper.insert(reserve);

        // 把对应实验室的状态变成 已预约
        Lab lab = labMapper.selectById(reserve.getLabId());
        lab.setStatus(StatusEnum.NO.getDesc());
        labMapper.updateById(lab);
    }

    /**
     * 取消预约
     */
    public void deleteById(Integer id) {
        Reserve reserve = reserveMapper.selectById(id);
        reserveMapper.deleteById(id);
        // 还原实验室的状态
        Lab lab = labMapper.selectById(reserve.getLabId());
        lab.setStatus(StatusEnum.OK.getDesc());
        labMapper.updateById(lab);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            reserveMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Reserve reserve) {
        if (ReserveEnum.DONE.getDesc().equals(reserve.getDostatus()) || ReserveEnum.NO.getDesc().equals(reserve.getStatus())) {
            // 还原实验室的状态
            Lab lab = labMapper.selectById(reserve.getLabId());
            lab.setStatus(StatusEnum.OK.getDesc());
            labMapper.updateById(lab);
        }
        reserveMapper.updateById(reserve);
    }

    /**
     * 根据ID查询
     */
    public Reserve selectById(Integer id) {
        return reserveMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Reserve> selectAll(Reserve reserve) {
        return reserveMapper.selectAll(reserve);
    }

    /**
     * 分页查询
     */
    public PageInfo<Reserve> selectPage(Reserve reserve, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.LABADMIN.name().equals(currentUser.getRole())) {
            reserve.setLabadminId(currentUser.getId());
        }
        if (RoleEnum.STUDENT.name().equals(currentUser.getRole())) {
            reserve.setStudentId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Reserve> list = reserveMapper.selectAll(reserve);
        return PageInfo.of(list);
    }

}