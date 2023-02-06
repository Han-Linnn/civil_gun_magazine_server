package com.jingde.equipment.app.duty.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.duty.dto.DutyLogDTO;
import com.jingde.equipment.app.duty.dto.HandoverDTO;
import com.jingde.equipment.app.duty.service.DutyLogService;
import com.jingde.equipment.app.record.dto.LableDTO;
import com.jingde.equipment.app.user.service.UserService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.DutyLog;
import com.jingde.equipment.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Goldrepo on 2019/2/28
 * 进出库/值班登记
 *
 * @author
 */
@RestController
@RequestMapping("/api/onDuty")
public class OnDutyController {

    //值班交接
    @Resource
    private DutyLogService dutyLogService;

    @Resource
    private UserService userService;

    /**
     * 值班交接日志
     * /api/onDuty/log1
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/log1")
    @LoginRequired
    public Result onDutyLogList1(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<DutyLog> list = dutyLogService.list();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 值班交接日志(按啊潮api)
     * /api/onDuty
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping()
    public Result onDutyLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        Result result = dutyLogService.onDutyLogList(page, size);
        return result;
    }

    /**
     * 获取值班交接最后一条记录
     * /api/onDuty/last
     * @return
     */
    @GetMapping(value = "/last", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result lastDutyLog() {
        Result result = dutyLogService.lastDutyLog();
        return result;
    }

    /**
     * 值班交接时的各类枪弹的结余数量
     * /api/onDuty
     *
     * @return
     */
    @PostMapping()
    @LoginRequired
    public Result result(@RequestBody DutyLogDTO vo) {
        dutyLogService.add(vo);
        return ResultGenerator.genSuccessResult();

    }

    /**
     * 值班交接时的各类枪弹的结余数量编辑
     * /api/onDuty
     *
     * @return
     */
    @PutMapping()
    @LoginRequired
    public Result update(@RequestBody DutyLogDTO vo) {
        if (null == vo || null == vo.getId()) {
            return ResultGenerator.genNoContentResult("请选择修改的对象！！！");
        }
        dutyLogService.update(vo);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 值班交接数据删除
     * /api/onDuty
     *
     * @return
     */
    @DeleteMapping()
    @LoginRequired
    public Result delete(@CurrentUser User user, @RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            String msg = "至少选择一个对象！！！";
            return ResultGenerator.genNoContentResult(msg);
        }
        dutyLogService.removeById(ids);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 交班人签名
     * /api/onDuty/handover
     * @param form
     * @return
     */
    @PostMapping("/handover")
    @LoginRequired
    public Result handover(@RequestBody HandoverDTO form) {
        List<LableDTO> lableList = form.getList();
        if (null == lableList || 2 != lableList.size()) {
            throw new ServiceException("交班人为空,或交班人不为两个人");
        }
        List<String> list = userService.selectUserByCode(lableList);
        if (2 != list.size()) {
            throw new ServiceException("需要两个交班人验证");
        }
        DutyLog dutyLog = new DutyLog();
        dutyLog.setId(form.getId());
        String[] person = list.get(0).split("-");
        dutyLog.setOffDutyPersonId(person[0]);
        dutyLog.setOffDutyPerson(person[1]);
        String[] person1 = list.get(1).split("-");
        dutyLog.setOffDutyPerson2Id(person1[0]);
        dutyLog.setOffDutyPerson2(person1[1]);
        dutyLogService.saveOrUpdate(dutyLog);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 接班人签名
     * /api/onDuty/carry
     * @param form
     * @return
     */
    @PostMapping("/carry")
    @LoginRequired
    public Result carryOn(@RequestBody HandoverDTO form) {
        List<LableDTO> lableList = form.getList();
        if (null == lableList || 2 != lableList.size()) {
            throw new ServiceException("接班人为空,或接班人不为两个人");
        }
        List<String> list = userService.selectUserByCode(lableList);
        if (2 != list.size()) {
            throw new ServiceException("需要两个接班人验证");
        }
        DutyLog dutyLog = new DutyLog();
        dutyLog.setId(form.getId());
        String[] person = list.get(0).split("-");
        dutyLog.setOnDutyPersonId(person[0]);
        dutyLog.setOnDutyPerson(person[1]);
        String[] person1 = list.get(1).split("-");
        dutyLog.setOnDutyPerson2Id(person1[0]);
        dutyLog.setOnDutyPerson2(person1[1]);
        dutyLogService.saveOrUpdate(dutyLog);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 值班交接时的各类枪弹的结余数量
     * /api/onDuty/result1
     *  TODO 需要验证有没有使用
     *
     * @return
     */
    @GetMapping("/result1")
    @LoginRequired
    public Result result1() {
        dutyLogService.updateNewest();
        return ResultGenerator.genSuccessResult();
    }


}
