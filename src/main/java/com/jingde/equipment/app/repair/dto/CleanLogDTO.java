package com.jingde.equipment.app.repair.dto;

import com.jingde.equipment.app.record.dto.LableDTO;
import lombok.Data;

import java.util.List;

@Data
public class CleanLogDTO {

    private Integer id;
    
    /**
     * 申请人
     */
    private String applyPerson;
    /**
     * 申请人id
     */
    private String applyPersonId;
    
    /**
     * 擦拭时间
     */
    private String cleanDate;

    /**
     * 保养原因
     */
    private String reason;

    /**
     * 备注
     */
    private String note;

    /**
     * 保养状态
     */
    private String status;

    /**
     * 保养不通过原因
     */
    private String auditingReason;
    //审批人id
    private Integer auditingPersonId;
    private String auditingPerson;
    private String password;
    /**
     * 保养详情
     */
    private List<CleanLogInfoDTO> list;

    //保管人签名集合
    private List<LableDTO> person;
    

}
