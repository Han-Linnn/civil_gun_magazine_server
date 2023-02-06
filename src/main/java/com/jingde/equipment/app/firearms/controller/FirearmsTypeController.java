package com.jingde.equipment.app.firearms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.cabinets.service.CabinetSeatService;
import com.jingde.equipment.app.cabinets.vo.FirearmNoVO;
import com.jingde.equipment.app.cabinets.vo.RegisterListVO;
import com.jingde.equipment.app.cabinets.vo.TotalVO;
import com.jingde.equipment.app.firearms.dto.FirearmsChangeLogDTO;
import com.jingde.equipment.app.firearms.dto.FirearmsEquipmentDTO;
import com.jingde.equipment.app.firearms.dto.FirearmsTypeDTO;
import com.jingde.equipment.app.firearms.dto.PoliceEquipmenlogDTO;
import com.jingde.equipment.app.firearms.service.FirearmsChangeLogService;
import com.jingde.equipment.app.firearms.service.FirearmsTypeService;
import com.jingde.equipment.app.firearms.vo.FirearmsTypeVO;
import com.jingde.equipment.app.police.service.PoliceEquipmentLogService;
import com.jingde.equipment.app.police.vo.PoliceEquipmenlogVO;
import com.jingde.equipment.app.repair.service.CleanLogService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.CleanLog;
import com.jingde.equipment.model.FirearmsType;
import com.jingde.equipment.model.PoliceEquipmentLog;
import com.jingde.equipment.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 枪支类型管理
 *
 * @author
 */
@RestController
@RequestMapping("/api/firearms/type")
public class FirearmsTypeController {

    @Resource
    private FirearmsTypeService firearmsTypeService;
    @Autowired
    private CleanLogService cleanLogService;
    @Resource
    private FirearmsChangeLogService firearmsChangeLogService;
    @Resource
    private PoliceEquipmentLogService policeEquipmentLogService;

    @Resource
    private CabinetSeatService cabinetSeatService;

    /**
     * 枪号缩略表
     * /api/firearms/no/list
	 *
     */
    @GetMapping("/no/list")
    public Result firearmsNoList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        List<String> list = policeEquipmentLogService.getfirearmsNoList();
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 枪支变更登记数据列表
     * /api/firearms/changeLog
     * @param page
     * @param size
     * @return
     */
    @LoginRequired
    @GetMapping("/changeLog")
    public Result changeLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {

        PageHelper.startPage(page, size);
        RegisterListVO vo = firearmsChangeLogService.select();
        PageInfo pageInfo = new PageInfo(vo.getList());
        vo.setList(pageInfo.getList());
        vo.setTotal((int) pageInfo.getTotal());
        vo.setPage(pageInfo.getPageNum());
        vo.setSize(pageInfo.getPageSize());
        return ResultGenerator.genSuccessResult(vo);
    }

    /**
     * 修改枪支变更登记数据
     * /api/firearms/changeLog
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PutMapping("/changeLog")
    public Result changeLogUpdate(@RequestBody FirearmsChangeLogDTO form) {
        firearmsChangeLogService.updateChangelog(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 新增枪支变更登记数据
     * /api/firearms/changeLog
	 *
	 * /api/firearms/changeLog
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("/changeLog")
    public Result changeLogInsert(@CurrentUser User currentUser, @RequestBody @Validated FirearmsChangeLogDTO form) {
        form.setRegisterName(currentUser.getPoliceName());
        firearmsChangeLogService.saveChangelog(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 枪支配备明细(列表)
     * /api/firearms/provideLog
	 *
	 * /api/firearms/provide
     *
     * @param page
     * @param size
     * @return
     */
    @LoginRequired
    @GetMapping("/provide")
    public Result provideLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size, @RequestParam(value = "id", required = false) Integer id) {
        if (null == id) {
            return policeEquipmentLogService.findPoliceEquipmenLogList(page, size);
        } else {
            PoliceEquipmenlogVO data = policeEquipmentLogService.findPoliceEquipmenLogById(id);
            return ResultGenerator.genSuccessResult(data);
        }
    }

    /**
     * 新增枪支配备
     * /api/firearms/provide
	 *
	 * /api/firearms/provide
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("/provide")
    public Result provide(@CurrentUser User user, @RequestBody PoliceEquipmenlogDTO form) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        PoliceEquipmentLog policeEquipmenlog = new PoliceEquipmentLog();
        if (null == form.getDate()) {
            policeEquipmenlog.setIssueDate(time);
        } else {
            policeEquipmenlog.setIssueDate(form.getDate());
        }
        policeEquipmenlog.setFirearmsType(form.getModel());
        policeEquipmenlog.setFirearmsNo(form.getGunNumber());
        policeEquipmenlog.setFirearmsCode(form.getCredentialsNumber());
        policeEquipmenlog.setUsedLog(form.getTrace());
        policeEquipmenlog.setChangeResult(form.getCondition());
        policeEquipmenlog.setNote(form.getRemark());
        policeEquipmenlog.setManagerPerson(user.getPoliceName());
        policeEquipmenlog.setAuditingPerson(form.getVerifier());
        policeEquipmenlog.setFirearmsTypeId(form.getModelId());
        form.setId(null);
        policeEquipmentLogService.save(policeEquipmenlog);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 枪支配备编辑
     * /api/firearms/provide
	 *
	 * /api/firearms/provide
     *
     * @param form
     * @return
     */
    @LoginRequired
    @PutMapping("/provide")
    public Result provideUpdate(@CurrentUser User user, @RequestBody PoliceEquipmenlogDTO form) {
        PoliceEquipmentLog policeEquipmenlog = new PoliceEquipmentLog();
        policeEquipmenlog.setIssueDate(form.getDate());
        policeEquipmenlog.setFirearmsType(form.getModel());
        policeEquipmenlog.setFirearmsTypeId(form.getModelId());
        policeEquipmenlog.setFirearmsNo(form.getGunNumber());
        policeEquipmenlog.setFirearmsCode(form.getCredentialsNumber());
        policeEquipmenlog.setManagerStatus(form.getState());
        policeEquipmenlog.setUsedLog(form.getTrace());
        policeEquipmenlog.setChangeResult(form.getCondition());
        policeEquipmenlog.setNote(form.getRemark());
        policeEquipmenlog.setManagerPerson(user.getPoliceName());
        policeEquipmenlog.setAuditingPerson(form.getVerifier());
        policeEquipmenlog.setId(form.getId());
        policeEquipmentLogService.saveOrUpdate(policeEquipmenlog);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 配备审核
     * /api/firearms/provide/auditing
     * @param
     * @return
     */
    @LoginRequired
    @RequiresPermissions("firearmsProvide:auditing")
    @PostMapping("/provide/auditing")
    public Result auditing(@CurrentUser User user, @RequestBody FirearmsEquipmentDTO form) {
        policeEquipmentLogService.auditing(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 擦拭保养日志列表
     * /api/firearms/repair
     * @param page
     * @param
     * @return
     */
    @LoginRequired
    @GetMapping("/repair")
    public Result repairLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<CleanLog> list = cleanLogService.list();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 枪支列表（值班交接标题）
     * /api/firearms/title
     * @return
     */
    @GetMapping("/title")
    public Result FirearmsTypeTitle() {
        return firearmsTypeService.firearmsTypeTitle();
    }

    // ==================  枪支类型 ===================

    /**
     * 枪支类型列表
     * /api/firearms/typeList
     * @param page
     * @param size
     * @return
     */
    @LoginRequired
    @GetMapping("/typeList")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "100") Integer size) {
        PageHelper.startPage(page, size);
        List<FirearmsTypeVO> list = firearmsTypeService.firearmsTypeList();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

	@LoginRequired
	@GetMapping("/select/list")
	public Result selectList(Integer type,String firearmsType) {
		List<FirearmsTypeVO> list = firearmsTypeService.selectList(type,firearmsType);
		return ResultGenerator.genSuccessResult(list);
	}


	/**
     * 获取柜子中有的枪支类型列表
     * /api/firearms/available
     * @param page
     * @param size
     * @param type (获取枪支情况,0:获取常用,1:获取所有可用)
     * @return
     */
    @LoginRequired
    @GetMapping("/available")
    public Result availableList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") String type) {
        PageHelper.startPage(page, size);
        List<FirearmsTypeVO> list = firearmsTypeService.availableList(type);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 添加枪支类型
     * /api/firearms/type
     * @param form
     * @return
     */
    @LoginRequired
    @PostMapping("")
    public Result add(@CurrentUser User currentUser, @RequestBody @Validated FirearmsTypeDTO form) {
        String registerPerson = currentUser.getPoliceName();
        firearmsTypeService.addFirearmsType(registerPerson, form);

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 删除枪支类型
     * /api/firearms/type/{id}
     * @param id
     * @return
     */
    @LoginRequired
    @DeleteMapping("/typeDel/{id}")
    public Result delete(@PathVariable Integer id) {
        if (null == id) {
            throw new ServiceException("枪支类型id不能为空");
        }
        firearmsTypeService.removeFirearmType(id);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 更新枪支类型
     * /api/firearms/type
     * @param form
     * @return
     */
    @LoginRequired
    @PutMapping("")
    public Result update(@RequestBody FirearmsTypeDTO form) {
        FirearmsType firearmsType = new FirearmsType();
        if (null == form.getFirearmsType()) {
            throw new ServiceException("枪支类型名称不能为空");
        }
        if (null == form.getId() || 0 == form.getId()) {
            throw new ServiceException("枪支类型id不能为空");
        }
        //修改枪支类型
        firearmsTypeService.saveOrUpdate(firearmsType);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 枪支类型详情
     * /api/firearms/type/{id}
     * @param id
     * @return
     */
    @LoginRequired
    @GetMapping("/typeList/{id}")
    public Result detail(@PathVariable Integer id) {
        FirearmsType firearmsType = firearmsTypeService.getById(id);
        return ResultGenerator.genSuccessResult(firearmsType);
    }

    /**
     * 获取所有枪号
     * /api/firearms/gunNoList
     * @return
     */
    @LoginRequired
    @GetMapping("/gunNoList")
    public Result getFireArmNoList() {
        List<FirearmNoVO> fireArmNoList = cabinetSeatService.getFireArmNoList();
        return ResultGenerator.genSuccessResult(fireArmNoList);
    }

    /**
     * 根据枪型,获取可用枪号
     * /api/firearms/gunNoList/type
     * @return
     * 需剔除接口
     */
    @LoginRequired
    @GetMapping("/gunNoList/type")
    public Result getFireArmNoListByType(String gunType) {
        List<FirearmNoVO> fireArmNoList = cabinetSeatService.getFireArmNoListByType(gunType);
        return ResultGenerator.genSuccessResult(fireArmNoList);
    }

    /**
     * 统计枪柜中枪支弹药
     * /api/firearms/total
     * @param
     */
    @LoginRequired
    @GetMapping("/total")
    public Result seelctTotal() {
        List<TotalVO> totalVOS = cabinetSeatService.selectTotalByType();
        return ResultGenerator.genSuccessResult(totalVOS);
    }

    /**
     * 获取指定时间,枪支类型的可操作性数量
     * url:/api/firearms/type/apply/number
     * @param time
     * @param typeId
     * @return
     */
    @LoginRequired
    @GetMapping("/apply/number")
    public Result selectByTimeAndId(String time,Integer typeId) {
        if (StringUtils.isBlank(time)) {
            return ResultGenerator.genFailResult("请选择时间");
        }
        List<FirearmsTypeVO> list = firearmsTypeService.selectByTimeAndId(time,typeId);
        return ResultGenerator.genSuccessResult(list);

    }

}
