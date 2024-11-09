package com.wxj.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.wxj.common.Result;
import com.wxj.common.enums.ResultCodeEnum;
import com.wxj.common.enums.RoleEnum;
import com.wxj.entity.Account;
import com.wxj.service.AdminService;
import com.wxj.service.LabadminService;
import com.wxj.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 基础前端接口
 */
@RestController
public class WebController {

    @Resource
    private AdminService adminService;
    @Resource
    private LabadminService labadminService;
    @Resource
    private StudentService studentService;

    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        String role = account.getRole();
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(role)) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(role)) {
            account = adminService.login(account);
        } else if (RoleEnum.LABADMIN.name().equals(account.getRole())) {
            account = labadminService.login(account);
        } else if (RoleEnum.STUDENT.name().equals(account.getRole())) {
            account = studentService.login(account);
        }

        return Result.success(account);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.register(account);
        } else if (RoleEnum.STUDENT.name().equals(account.getRole())) {
            studentService.register(account);
        }
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        } else if (RoleEnum.LABADMIN.name().equals(account.getRole())) {
            labadminService.updatePassword(account);
        } else if (RoleEnum.STUDENT.name().equals(account.getRole())) {
            studentService.updatePassword(account);
        }
        return Result.success();
    }

}
