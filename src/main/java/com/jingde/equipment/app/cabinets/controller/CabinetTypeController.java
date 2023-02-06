package com.jingde.equipment.app.cabinets.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.cabinets.service.CabinetTypeService;
import com.jingde.equipment.model.Cabinet;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.app.cabinets.dto.CabinetDTO;
import com.jingde.equipment.model.Equipmencategory;
import com.jingde.equipment.model.User;
import com.jingde.equipment.app.system.vo.clean.ListDataVO;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 枪柜类型管理
 * @author
 */
@RestController
@RequestMapping("/api/cabinet")
public class CabinetTypeController {

    @Resource
    private CabinetTypeService cabinetTypeService;

    /**
     * 枪柜类型添加
     * /api/cabinet/firearms
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("/firearms")
    public Result add(@CurrentUser User currentUser, @RequestBody @Validated CabinetDTO form) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Equipmencategory c = new Equipmencategory();
        if (null == form.getRegisterDate()) {
            form.setRegisterDate(df.format(new Date()));
        }
        if (null == form.getEquipmentCategory()) {
            throw new ServiceException("枪柜类型名称不能为空");
        }
        // FIXME
        form.setRegisterPerson(currentUser.getPoliceName());
        BeanUtils.copyProperties(form, c);
        cabinetTypeService.save(c);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 枪柜类型删除
     * /api/cabinet/firearms
     *
     * @param id
     * @return
     */
    @LoginRequired
    @DeleteMapping("/firearms")
    public Result delete(String id) {
        if (null == id) {
            throw new ServiceException("枪柜类型id不能为空");
        }
        cabinetTypeService.removeById(Integer.parseInt(id));
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 枪柜类型更新
     * /api/cabinet/firearms
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PutMapping("/firearms")
    public Result update(@RequestBody CabinetDTO form) {
        Equipmencategory c = new Equipmencategory();
        if (null == form.getEquipmentCategory()) {
            throw new ServiceException("枪柜类型名称不能为空");
        }
        if (null == form.getId() || 0 == form.getId()) {
            throw new ServiceException("枪柜类型id不能为空");
        }
        BeanUtils.copyProperties(form, c);
        cabinetTypeService.saveOrUpdate(c);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 枪柜类型详情
     * /api/cabinet/firearms/{id}
     *
     * @param id
     * @return
     */
    @LoginRequired
    @GetMapping("/firearms/{id}")
    public Result detail(@PathVariable Integer id) {
        Equipmencategory info = cabinetTypeService.getById(id);
        return ResultGenerator.genSuccessResult(info);
    }

    /**
     * /api/cabinet/firearms
     * @param page
     * @param size
     * @return
     */
    @LoginRequired
    @GetMapping("/firearms")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<Equipmencategory> list = cabinetTypeService.list();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * /api/cabinet/firearms/select
     * @return
     */
    @GetMapping("/firearms/select")
    public Result select() {
        List<Cabinet> list = cabinetTypeService.cabinetSelect();
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * /api/cabinet/firearms/details
     * @return
     */
    @GetMapping("/firearms/details")
    public Result equipmentDetail(String cabinetCodes) {
        String[] split = cabinetCodes.split(",");
        if (split.length < 1) {
            return ResultGenerator.genFailResult("请检查参数");
        }
        List<ListDataVO> list = cabinetTypeService.equipmentDetail(split);
        return ResultGenerator.genSuccessResult(list);
    }
}
