package com.jingde.equipment.app.repair.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.repair.dto.CleanLogDTO;
import com.jingde.equipment.app.repair.service.CleanLogInfoService;
import com.jingde.equipment.app.repair.service.CleanLogService;
import com.jingde.equipment.app.repair.vo.CleanLogVo;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.CleanLogInfo;
import com.jingde.equipment.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 枪支擦拭保养管理
 *
 * @author
 */
@RestController
@RequestMapping("/api/clean/repair")
public class CleanController {

    @Resource
    private CleanLogService cleanLogService;
    @Resource
    private CleanLogInfoService cleanLogInfoService;



    /**
     * 1.擦拭保养申请
     * url:/api/clean/repair
     *
     * @param form
     * @return
     */
    @PostMapping("")
    @LoginRequired
    public Result repairRegister(@CurrentUser User user, @RequestBody CleanLogDTO form) {
        if (StringUtils.isBlank(form.getCleanDate())) {
            return ResultGenerator.genFailResult("保养时间(cleanDate)为空");
        }
        if (StringUtils.isBlank(form.getReason())) {
            return ResultGenerator.genFailResult("保养原因(reason)为空");
        }
        if (null == form.getList() || form.getList().size() <= 0) {
            return ResultGenerator.genFailResult("请选择需要保养的枪支");
        }
        cleanLogService.addCleanLog(user, form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 2.擦拭保养编辑
     * url:/api/clean/repair(put)
     *
     * @param form
     * @return
     */
    @PutMapping("")
    @LoginRequired
    public Result repairUpdate(@RequestBody CleanLogDTO form) {
        if (null == form.getId()) {
            return ResultGenerator.genFailResult("请选择要修改的申请");
        }
        cleanLogService.updateCleanLog(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 3.擦拭保养删除
     * url:/api/clean/repair/{id} (delete)
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    @LoginRequired
    public Result deleteComeOut(@PathVariable String id) {
        if (StringUtils.isBlank(id)) return ResultGenerator.genNoContentResult("至少选择一个对象！！！");
        //删除保养记录
        cleanLogService.removeById(id);
        //删除保养详情
        QueryWrapper<CleanLogInfo> wrapper = new QueryWrapper();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("clean_log_id", id);
        wrapper.allEq(queryMap);
        cleanLogInfoService.remove(wrapper);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 4.擦拭保养日志列表
     * url:/api/clean/repair/list
     *
     * @param page
     * @param
     * @return
     */
    @GetMapping("/list")
    @LoginRequired
    public Result repairLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String status) {
        PageHelper.startPage(page, size);
        PageInfo pageInfo = cleanLogService.listByPage(status);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 5.擦拭保养审核
     * url:/api/clean/repair/auditing (put)
     *
     * @param
     * @return
     */
    @PutMapping("/auditing")
    @LoginRequired
    public Result repairAuditing(@CurrentUser User user, @RequestBody CleanLogDTO form) {
        System.out.println(form.getId());
        if (null == form.getId()) {
            return ResultGenerator.genFailResult("请选择需要审核的保养申请");
        }
        if (null == form.getAuditingPersonId()) {
            return ResultGenerator.genFailResult("请选择审批人");
        }
        if (StringUtils.isBlank(form.getPassword())) {
            return ResultGenerator.genFailResult("密码为空");
        }
        cleanLogService.updateByAuditing(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 6.保养状态更新
     * <p>
     * 1.待审批改为取消
     * 2.待保养改为保养中
     * 3.保养中改为保养完成
     * url:/api/clean/repair/update/status(put)
     *
     * @param form
     * @return
     */
    @PutMapping("/update/status")
    @LoginRequired
    public Result repairStart(@RequestBody CleanLogDTO form) {
        if (null == form.getId()) {
            return ResultGenerator.genFailResult("请选择保养申请");
        }
        //这里处理提交
        cleanLogService.updateStartById(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 保养详情
     * url:/api/clean/repair/{id}(get)
     */
    @GetMapping("/{id}")
    @LoginRequired
    public Result findInfoById(@PathVariable String id) {
        if (StringUtils.isBlank(id)) return ResultGenerator.genNoContentResult("至少选择一个对象！！！");
        CleanLogVo vo = cleanLogService.findInfoById(id);
        return ResultGenerator.genSuccessResult(vo);
    }
}
