package com.wxj.controller;

import com.github.pagehelper.PageInfo;
import com.wxj.common.Result;
import com.wxj.entity.Labadmin;
import com.wxj.service.LabadminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/labadmin")
public class LabadminController {

    @Resource
    private LabadminService labadminService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Labadmin labadmin) {
        labadminService.add(labadmin);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        labadminService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        labadminService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Labadmin labadmin) {
        labadminService.updateById(labadmin);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Labadmin labadmin = labadminService.selectById(id);
        return Result.success(labadmin);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Labadmin labadmin ) {
        List<Labadmin> list = labadminService.selectAll(labadmin);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Labadmin labadmin,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Labadmin> page = labadminService.selectPage(labadmin, pageNum, pageSize);
        return Result.success(page);
    }

}