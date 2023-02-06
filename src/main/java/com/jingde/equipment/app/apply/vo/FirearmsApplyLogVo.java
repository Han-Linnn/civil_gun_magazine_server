package com.jingde.equipment.app.apply.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FirearmsApplyLogVo {

    private Integer id;

    /**
     * 预计领用时间
     */
    private String applyDate;

    /**
     * 领用理由
     */
    private String applyReason;

    /**
     * 领枪人id
     */
    private Integer applyPersonId;

    /**
     * 领枪人
     */
    private String applyPerson;

    /**
     * 审批人 id
     */
    private Integer auditingPersonId;

    /**
     * 审批人
     */
    private String auditingPerson;

    /**
     * 审批时间
     */
    private String auditingDate;

    /**
     * 预计归还日期
     */
    private String returnDate;

    /**
     * 初次预计交还时间
     */
    private String firstReturnDate;

    /**
     * 延时审批人id
     */
    private Integer delayApproverId;

    /**
     * 延时审批人
     */
    private String delayApprover;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String note;


    /**
     * 枪炮类型
     */
    private List<FirearmsApplyTypeLogVo> types;

    /**
     * 借枪批次
     */
    private List<FirearmsReceiveBatchVo> receiveBatchs;

    /**
     * 还枪批次
     */
    private List<FirearmsReturnBatchVo> returnBatchs;

}
