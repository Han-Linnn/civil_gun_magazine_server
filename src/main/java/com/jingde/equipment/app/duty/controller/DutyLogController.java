/*
package com.jingde.equipment.app.duty.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.duty.service.DutyLogService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.DutyLog;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

*/
/**
* Created by oceanover on 2019/03/11.
 *  TODO 确认是否没有用,没有就删除
*//*


@RestController
@RequestMapping("/api/duty/log")
public class DutyLogController {
    @Resource
    private DutyLogService dutyLogService;

    @PostMapping
    public Result add(@RequestBody DutyLog dutyLog) {
        dutyLogService.save(dutyLog);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        dutyLogService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody DutyLog dutyLog) {
        dutyLogService.saveOrUpdate(dutyLog);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        DutyLog dutyLog = dutyLogService.getById(id);
        return ResultGenerator.genSuccessResult(dutyLog);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<DutyLog> list = dutyLogService.list();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
*/
