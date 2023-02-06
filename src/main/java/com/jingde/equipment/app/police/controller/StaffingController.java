package com.jingde.equipment.app.police.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.police.service.PoliceService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.app.police.dto.StaffingDTO;
import com.jingde.equipment.model.Police;
import com.jingde.equipment.app.police.service.PoliceEquipmenusedService;
import com.jingde.equipment.app.police.service.UnhealthyRecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Goldrepo on 2019/2/28
 * 配枪人员管理
 */
@RestController
@RequestMapping("/api/staffing")
public class StaffingController {

    @Resource
    private PoliceEquipmenusedService policeEquipmenusedService;

    @Resource
    private UnhealthyRecordService unhealthyRecordService;

    @Resource
    private PoliceService policeService;

    /**
     * 配枪人员列表(type: 1-允许,0-禁止)
     * staffing/list
     *
     * @param page
     * @param size
     * @return
     */
    @LoginRequired
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer type) {
        PageHelper.startPage(page, size);
        PageInfo pageInfo = policeEquipmenusedService.selectPageBytype(type);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 登记
     * staffing/register(type: 1-允许,0-禁止)
     *
     * @param form
     * @return
     */
    @LoginRequired
    @RequiresPermissions({"staffing:register"})
    @PostMapping("/register")
    public Result register(@RequestBody @Validated StaffingDTO form) {
        if (StringUtils.isBlank(form.getType()) || (!"0".equals(form.getType().trim()) && !"1".equals(form.getType().trim()))) {
            return ResultGenerator.genFailResult("type为空");
        }
        if (StringUtils.isBlank(form.getPoliceCode())) {
            return ResultGenerator.genFailResult("policeCode为空");
        }
        if ("0".equals(form.getType()) && StringUtils.isBlank(form.getReason())) {
            return ResultGenerator.genFailResult("reason为空");
        }
        List<Police> select = policeService.select(null, form.getPoliceCode());
        if (null == select || select.size() <=0 ) {
            return ResultGenerator.genFailResult("该警员不存在");
        }
        Police vo = select.get(0);
        if (form.getType().equals("1") && vo.getUseGunStatus() == 0) {
            return ResultGenerator.genFailResult("该警员已经是持枪状态,无需修改");
        }
        if (form.getType().equals("0") && vo.getUseGunStatus() == 1) {
            return ResultGenerator.genFailResult("该警员已经是禁枪状态,无需修改");
        }
        //保存持枪状态
        policeEquipmenusedService.saveOrUpdateBypoliceCode(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 删除禁枪人员,修改用户为可以用枪用枪(解除禁止用枪)
     * staffing?ids=1,2
     * @param ids
     * @return
     */
    @LoginRequired
    @RequiresPermissions({"staffing:relieve"})
    @DeleteMapping("/info")
    public Result remove( String ids) {
        if (StringUtils.isBlank(ids))
            return ResultGenerator.genFailResult("用户为空");
        //允许用枪
        Integer status = 0;
        //如果删除禁枪人员,修改用户为可以用枪用枪
        policeService.updateUseGunStatusByPeuId(ids, status);
        return ResultGenerator.genSuccessResult();
    }

}
