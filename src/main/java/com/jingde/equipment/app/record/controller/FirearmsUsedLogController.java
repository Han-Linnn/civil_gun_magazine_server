package com.jingde.equipment.app.record.controller;

import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.record.service.FirearmsUsedLogService;
import com.jingde.equipment.app.record.vo.LableVo;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by oceanover on 2019/03/07.
 * 用枪领用流程
 *
 * @author
 */
@RestController
@RequestMapping("/api/firearms/usedlog")
public class FirearmsUsedLogController {

    @Resource
    private FirearmsUsedLogService firearmsUsedLogService;


    /**
     * 申领单统计
     * /api/firearms/usedlog/total
     */
    @LoginRequired
    @GetMapping("/total")
    public Result total() {
        List<LableVo> list = firearmsUsedLogService.total();
        return ResultGenerator.genSuccessResult(list);
    }

}
