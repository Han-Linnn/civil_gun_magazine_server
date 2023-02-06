package com.jingde.equipment.app.repair.vo;

import com.jingde.equipment.model.CleanLogInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CleanLogVo {

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
     * 审批人
     */
    private String auditingPerson;

    private Integer firearmCount;

    /**
     * 擦拭时间
     */
    private String cleanDate;
    /**
     * 擦拭保养人(xx-xx-xx-.....)
     */
    private String cleanPerson;

    /**
     * 保养完成人
     */
    private String cleanCompletePerson;
    /**
     * 保养原因
     */
    private String reason;
    /**
     * 保养状态
     */
    private String status;

    /**
     * 备注
     */
    private String note;

    /**
     * 审核不通过原因
     */
    private String auditingReason;

    /**
     * 保养统计
     */
    private Map<String,Integer> map;

    /**
     * 保养详情
     */
    private  List<CleanLogInfoVo> list;



}
