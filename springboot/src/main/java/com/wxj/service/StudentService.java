package com.wxj.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxj.common.Constants;
import com.wxj.common.enums.ResultCodeEnum;
import com.wxj.common.enums.RoleEnum;
import com.wxj.entity.Account;
import com.wxj.entity.Student;
import com.wxj.exception.CustomException;
import com.wxj.mapper.StudentMapper;
import com.wxj.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;

    public Account login(Account account) {
        Account dbStudent = studentMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbStudent.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbStudent.getId() + "-" + RoleEnum.STUDENT.name();
        String token = TokenUtils.createToken(tokenData, dbStudent.getPassword());
        dbStudent.setToken(token);
        return dbStudent;
    }

    public void register(Account account) {
        Student student = new Student();
        BeanUtils.copyProperties(account, student);
        add(student);
    }

    public void updatePassword(Account account) {
        Student dbStudent = studentMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbStudent.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbStudent.setPassword(account.getNewPassword());
        studentMapper.updateById(dbStudent);
    }

    /**
     * 新增
     */
    public void add(Student student) {
        Student dbStudent = studentMapper.selectByUsername(student.getUsername());
        if (ObjectUtil.isNotNull(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(student.getPassword())) {
            student.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(student.getName())) {
            student.setName(student.getUsername());
        }
        student.setRole(RoleEnum.STUDENT.name());
        studentMapper.insert(student);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        studentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            studentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Student student) {
        studentMapper.updateById(student);
    }

    /**
     * 根据ID查询
     */
    public Student selectById(Integer id) {
        return studentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Student> selectAll(Student student) {
        return studentMapper.selectAll(student);
    }

    /**
     * 分页查询
     */
    public PageInfo<Student> selectPage(Student student, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> list = studentMapper.selectAll(student);
        return PageInfo.of(list);
    }
}