package com.jingde.equipment.app.police.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.model.User;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.app.police.dto.PoliceUnhealthyRecordDTO;
import com.jingde.equipment.model.PoliceUnhealthyRecord;
import com.jingde.equipment.app.police.service.UnhealthyRecordService;
import com.jingde.equipment.app.police.vo.PoliceUnhealthyRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 人员不良记录管理
 * @author
 */
//@RestController
//@RequestMapping("/api/unhealthy")
public class UnhealthyRecordController {

    @Resource
    private UnhealthyRecordService unhealthyRecordService;

    /**
     * 不良记录列表
     * /unhealthy/page
     * @param
     * @param
     * @return
     */
    @LoginRequired
    @GetMapping("/page")
    public Result ListByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,@CurrentUser User user) {
        PageHelper.startPage(page, size);
        List<PoliceUnhealthyRecordVO> list = unhealthyRecordService.findByPage(user.getId());
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 人员不良记录添加
     * /unhealthy/info
     * @param
     * @param
     * @return
     */
    @LoginRequired
    @PostMapping("/info")
    public Result add(@RequestBody PoliceUnhealthyRecordDTO form) {
        unhealthyRecordService.add(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 人员不良记录更新
     * /unhealthy/info
     * @param form
     * @return
     */
    @LoginRequired
    @PutMapping("/info")
    public Result update(@RequestBody PoliceUnhealthyRecordDTO form) {
        PoliceUnhealthyRecord info = new PoliceUnhealthyRecord();
        BeanUtils.copyProperties(form,info);
        unhealthyRecordService.saveOrUpdate(info);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 不良记录详情
     * /unhealthy/info
     * @param id
     * @return
     */
    @LoginRequired
    @GetMapping("/info")
    public Result detail(Integer id) {

        PoliceUnhealthyRecordVO info = unhealthyRecordService.findOneById(id);
        return ResultGenerator.genSuccessResult(info);
    }

    /**
     * 通过警员id获取警员不良记录信息
     * /unhealthy/info/code
     * @param
     * @return
     */
    @LoginRequired
    @GetMapping("/info/code")
    public Result findByPoliceId(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,String policeId,String reason) {
        PageHelper.startPage(page, size);
        List<PoliceUnhealthyRecordVO> list = unhealthyRecordService.findByPoliceId(policeId,reason);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
