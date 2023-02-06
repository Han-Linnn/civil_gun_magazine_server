package com.jingde.equipment.app.firearms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.cabinets.service.CabinetTypeService;
import com.jingde.equipment.app.cabinets.vo.RegisterListVO;
import com.jingde.equipment.app.firearms.dto.AmmoChangeLogDTO;
import com.jingde.equipment.app.firearms.dto.AmmoTypeDTO;
import com.jingde.equipment.app.firearms.service.AmmoChangeLogService;
import com.jingde.equipment.app.firearms.service.AmmoTypeService;
import com.jingde.equipment.app.firearms.vo.AmmoFirearmVO;
import com.jingde.equipment.app.firearms.vo.AmmoTypeVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.AmmoType;
import com.jingde.equipment.model.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Goldrepo on 2019/2/28
 * 弹药类型管理
 *
 * @author
 */
@RestController
@RequestMapping("/api/ammo")
public class AmmoTypeController {

    @Resource
    private AmmoTypeService ammoTypeService;

    @Resource
    private AmmoChangeLogService ammoChangeLogService;

    @Resource
    private CabinetTypeService cabinetTypeService;

    /**
     * 弹药变更登记数据列表
     * /api/ammo/changeLog
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/changeLog")
    public Result changeLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        RegisterListVO vo = ammoChangeLogService.select();
        PageInfo pageInfo = new PageInfo(vo.getList());
        vo.setList(pageInfo.getList());
        vo.setTotal((int) pageInfo.getTotal());
        vo.setPage(pageInfo.getPageNum());
        vo.setSize(pageInfo.getPageSize());
        return ResultGenerator.genSuccessResult(vo);
    }

    /**
     * 弹药变更登记数据修改
     * /api/ammo/changeLog
     *
     * @param form
     * @return
     * @RequestAttribute("currentUser") User currentUser,
     */
    @PutMapping("/changeLog")
    @LoginRequired
    public Result changeLogUpdate(@RequestBody AmmoChangeLogDTO form) {
        ammoChangeLogService.updateChangelog(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 弹药变更登记数据
     * /api/ammo/changeLog
     *
     * @param form
     * @return
     * @RequestAttribute("currentUser") User currentUser,
     */
    @PostMapping("/changeLog")
    @LoginRequired
    public Result changeLogInsert(@CurrentUser User currentUser, @RequestBody @Validated AmmoChangeLogDTO form) {
        form.setRegisterName(currentUser.getPoliceName());
        ammoChangeLogService.saveChangelog(form);
        return ResultGenerator.genSuccessResult();
    }

    // ====================  弹药类型 =======================

    /**
     * 弹药类型列表
     * /api/ammo/typeList
     * @param page
     * @param size
     * @return
     */
    @LoginRequired
    @GetMapping("/typeList")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<AmmoTypeVO> list = ammoTypeService.findAmmoType();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    /**
     * 弹药类型列表
     * /api/ammo/type/select
     * @param
     * @param
     * @return
     */
    @GetMapping("/type/select")
    public Result select(Integer type,Integer firearmsTypeId,String ammoType) {
        List<AmmoFirearmVO> list = ammoTypeService.select(type,firearmsTypeId,ammoType);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 弹药类型添加
     * /api/ammo/type
     *
     * @param form
     * @return
     */
    @PostMapping("/type")
    @LoginRequired
    public Result add(@CurrentUser User currentUser, @RequestBody @Validated AmmoTypeDTO form) {
        String registerPerson = currentUser.getPoliceName();
        ammoTypeService.addAmmoType(registerPerson, form);

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 弹药类型删除
     * /api/ammo/type/{id}
     *
     * @param id
     * @return
     */
    @DeleteMapping("/typeDel/{id}")
    public Result delete(@PathVariable Integer id) {
        if (null == id) {
            throw new ServiceException("弹药类型id不能为空");
        }
        ammoTypeService.removeAmmoType(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 弹药类型更新
     * /api/ammo/type
     *
     * @param form
     * @return
     */
    @PutMapping("/type")
    @LoginRequired
    public Result update( @RequestBody AmmoTypeDTO form) {
        if (null == form.getId() || 0 == form.getId()) {
            throw new ServiceException("弹药类型id不能为空");
        }

        ammoTypeService.updateAmmoType(form);

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 弹药类型详情
     * /api/ammo/type/{id}
     *
     * @param id
     * @return
     */
    @GetMapping("/typeList/{id}")
    public Result detail(@PathVariable Integer id) {
        AmmoType eBulletCategory = ammoTypeService.getById(id);
        return ResultGenerator.genSuccessResult(eBulletCategory);
    }

}
