package com.jingde.equipment.app.record.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 延时审批
 */
@Data
public class ExpectedDTO {
    @NotNull(message = "id不能为空")
    private Integer id;
    //延长交还时间
    private  String lengthenReturnDate;
    //延时审批人id
    private  Integer delayApproverId;
    //延时审批人
    private  String delayApprover;
    // 密码
    private String password;
}
