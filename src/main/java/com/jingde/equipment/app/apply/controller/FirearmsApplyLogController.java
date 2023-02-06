package com.jingde.equipment.app.apply.controller;

import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.apply.dto.FirearmsApplyLogDTO;
import com.jingde.equipment.app.apply.dto.FirearmsApplyLogForm;
import com.jingde.equipment.app.apply.dto.FirearmsReceiveBatchDTO;
import com.jingde.equipment.app.apply.dto.FirearmsReturnBatchDTO;
import com.jingde.equipment.app.apply.service.FirearmsApplyLogService;
import com.jingde.equipment.app.apply.vo.FirearmsApplyLogVo;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jingde.equipment.constant.ServiceConstant.APPLY_STATUS_CANCEL;
import static com.jingde.equipment.constant.ServiceConstant.APPLY_STATUS_RECEIVE_NOT_RETURN;

/**
 * @author Termite
 * 领用申请
 */

@RestController
@RequestMapping("/api/apply")
public class FirearmsApplyLogController {

    @Resource
    private FirearmsApplyLogService firearmsApplyLogService;

    /**
     * 新增领用申请
     * URL:/api/apply/add(post)
     */
    @PostMapping("/add")
    @LoginRequired
    public Result add(@CurrentUser User user, @RequestBody FirearmsApplyLogForm form) {
        form.setApplyPerson(user.getPoliceName());
        form.setApplyPersonId(user.getId());
        firearmsApplyLogService.add(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 修改领用申请
     * URL:/api/apply/update(put)
     */
    @PutMapping("/update")
    @LoginRequired
    public Result updateById(@CurrentUser User user, @RequestBody FirearmsApplyLogForm form) {
        if (null == form.getId()) {
            return ResultGenerator.genFailResult("id不能为空");
        }
        firearmsApplyLogService.updateById(user, form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 领用审申请审批
     * URL:/api/apply/approval(post)
     */
    @PostMapping("/approval")
    @LoginRequired
    public Result updateApprovalById(HttpServletRequest request,@CurrentUser User user, @RequestBody FirearmsApplyLogForm form) {
        firearmsApplyLogService.updateApprovalById(request,user,form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 领用申请状态更新
     * URL: /api/apply/status/update(put)
     */
    @PutMapping("/status/update")
    @LoginRequired
    public Result updateStatusById(Integer id, String status) {
        if (null == id) {
            return ResultGenerator.genFailResult("id不能为空");
        }
        if (StringUtils.isBlank(status) || (!APPLY_STATUS_CANCEL.equals(status) && !APPLY_STATUS_RECEIVE_NOT_RETURN.equals(status))) {
            return ResultGenerator.genFailResult("status不合法");
        }
        firearmsApplyLogService.updateStatusById(id, status);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 领用申请列表
     * URL:
     */
    @GetMapping("/page")
    @LoginRequired
    public Result selectPageByCondition(FirearmsApplyLogDTO dto) {
        PageInfo pageInfo = firearmsApplyLogService.selectPageByCondition(dto);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 领用申请详情
     * URL:/api/apply/info/{id}
     */
    @GetMapping("/info/{id}")
    public Result findInfoById(@PathVariable Integer id) {
        if (null == id) {
            return ResultGenerator.genFailResult("id不能为空");
        }
        FirearmsApplyLogVo vo = firearmsApplyLogService.findInfoById(id);
        return ResultGenerator.genSuccessResult(vo);
    }

    /**
     * 枪炮扫码出库
     * URL:/api/apply/scan/out(post)
     */
    @PostMapping("/scan/out")
    @LoginRequired
    public Result updateScanOutById(@RequestBody FirearmsReceiveBatchDTO dto) {
        if (null == dto.getApplyId()) {
            return ResultGenerator.genFailResult("申领记录id为空");
        }
        firearmsApplyLogService.updateScanOutById(dto);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 枪炮扫码入库
     * URL:/api/apply/scan/come(post)
     */
    @PostMapping("/scan/come")
    @LoginRequired
    public Result updateScanComeById(@RequestBody FirearmsReturnBatchDTO dto) {
        if (null == dto.getApplyId()) {
            return ResultGenerator.genFailResult("申领记录id为空");
        }
        firearmsApplyLogService.updateScanComeById(dto);
        return ResultGenerator.genSuccessResult();
    }

}
