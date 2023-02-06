package com.jingde.equipment.app.apply.controller;


import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.apply.service.FirearmsReceiveBatchService;
import com.jingde.equipment.app.apply.vo.FirearmsReceiveBatchVo;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 申请类型记录表 前端控制器
 *
 * @author termite
 * @since 2020-07-08
 */

@RestController
@RequestMapping("/api/apply/receive/batch")
public class FirearmsReceiveBatchController {

    @Resource
    private FirearmsReceiveBatchService firearmsReceiveBatchService;

    /**
     * 批次领取情况
     */
    @LoginRequired
    @GetMapping("/info/{id}")
    public Result findInfoById(@PathVariable Integer id) {
        if (null == id) {
            return ResultGenerator.genFailResult("id不能为空");
        }
        FirearmsReceiveBatchVo vo = firearmsReceiveBatchService.findListById(id);
        return ResultGenerator.genSuccessResult(vo);
    }


}
