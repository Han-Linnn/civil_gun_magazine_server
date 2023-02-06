package com.jingde.equipment.app.apply.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.apply.dto.FirearmsApplyLogDTO;
import com.jingde.equipment.app.apply.dto.FirearmsApplyLogForm;
import com.jingde.equipment.app.apply.dto.FirearmsReceiveBatchDTO;
import com.jingde.equipment.app.apply.dto.FirearmsReturnBatchDTO;
import com.jingde.equipment.app.apply.vo.FirearmsApplyLogVo;
import com.jingde.equipment.app.apply.vo.FirearmsLogDetailVO;
import com.jingde.equipment.app.apply.vo.FirearmsLogVO;
import com.jingde.equipment.model.FirearmsApplyLog;
import com.jingde.equipment.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FirearmsApplyLogService extends IService<FirearmsApplyLog> {

    void add(FirearmsApplyLogForm form);

    void updateById(User user, FirearmsApplyLogForm form);

    void updateApprovalById(HttpServletRequest request,User user, FirearmsApplyLogForm form);

    void updateStatusById(Integer id, String status);

    PageInfo selectPageByCondition(FirearmsApplyLogDTO dto);

    FirearmsApplyLogVo findInfoById(Integer id);

    void updateScanOutById(FirearmsReceiveBatchDTO dto);

    void updateScanComeById(FirearmsReturnBatchDTO dto);

    List<FirearmsLogVO> getFirearmsLog();

    List<FirearmsLogDetailVO> getFirearmsLogDetail(Integer id);
}
