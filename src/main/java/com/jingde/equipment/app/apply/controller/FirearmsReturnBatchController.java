package com.jingde.equipment.app.apply.controller;


import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.apply.service.FirearmsReturnBatchService;
import com.jingde.equipment.app.apply.vo.FirearmsReturnBatchVo;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 申请类型记录表 前端控制器
 * </p>
 *
 * @author jingde
 * @since 2020-07-08
 */
@RestController
@RequestMapping("/api/apply/return/batch")
public class FirearmsReturnBatchController {

    @Resource
    private FirearmsReturnBatchService firearmsReturnBatchService;

    /**
     * 批次领取情况
     */
    @GetMapping("/info/{id}")
    @LoginRequired
    public Result findInfoById(@PathVariable Integer id) {
        if (null == id) {
            return ResultGenerator.genFailResult("id不能为空");
        }
        FirearmsReturnBatchVo vo = firearmsReturnBatchService.findListById(id);
        return ResultGenerator.genSuccessResult(vo);
    }

}
