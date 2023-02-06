package com.jingde.equipment.app.duty.controller;

import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.duty.dto.ComeOutlogDTO;
import com.jingde.equipment.app.duty.service.ComeOutLogService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.ComeOutlog;
import com.jingde.equipment.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/api/onDuty")
public class ComeOutLogController {

    //进出库
    @Resource
    private ComeOutLogService comeOutLogService;

    /**
     * 人员进出日志列表
     * onDuty/inAndOut/log
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/inAndOut")
    @LoginRequired
    public Result inAndOutLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        Result result = comeOutLogService.findComeOutList(page, size);
        return result;
    }

    /**
     * 人员进出登记
     * onDuty/inAndOut/register
     *
     * @param form
     * @return
     */
    @PostMapping("/inAndOut")
    @LoginRequired
    public Result inAndOutRegister(@CurrentUser User user, @RequestBody ComeOutlogDTO form) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        ComeOutlog comeOutlog = new ComeOutlog();
        comeOutlog.setPoliceName(form.getName());
        comeOutlog.setJob(form.getDuties());
        comeOutlog.setDepartment(form.getUnit());
        comeOutlog.setPartnerCount(form.getPeople());
        comeOutlog.setReason(form.getReason());
        comeOutlog.setCreateTime(new Date());
        if (null != form.getEntryDate()) {
            comeOutlog.setComeOutDate(form.getEntryDate());
        } else {
            comeOutlog.setComeOutDate(time);
        }
        comeOutlog.setComeOutResult(form.getSituation());
        comeOutlog.setNote(form.getNotes());
        if (null != form.getDate()) {
            comeOutlog.setRegisterDate(form.getDate());
        } else {
            comeOutlog.setRegisterDate(time);
        }
        comeOutlog.setRegisterName(user.getPoliceName());
        if (null == form.getId()) {
            //新增
            comeOutLogService.save(comeOutlog);
        } else {
            //编辑
            comeOutlog.setId(form.getId());
            comeOutLogService.saveOrUpdate(comeOutlog);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 人员进出编辑
     * onDuty/inAndOut/register
     *
     * @param form
     * @return
     */
    @PutMapping("/inAndOut")
    @LoginRequired
    public Result inAndOutUpdate(@CurrentUser User user, @RequestBody ComeOutlogDTO form) {
        ComeOutlog comeOutlog = new ComeOutlog();
        comeOutlog.setNote(form.getNotes());
        comeOutlog.setUpdateTime(new Date());
        //编辑
        comeOutlog.setId(form.getId());
        comeOutLogService.saveOrUpdate(comeOutlog);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 删除人员进出/检查记录
     *
     * @param user
     * @param ids
     * @return
     */
    @DeleteMapping("/inAndOut")
    @LoginRequired
    public Result deleteComeOut(@CurrentUser User user, @RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            String msg = "至少选择一个对象！！！";
            return ResultGenerator.genNoContentResult(msg);
        }
        comeOutLogService.removeById(ids);
        return ResultGenerator.genSuccessResult();
    }
}
