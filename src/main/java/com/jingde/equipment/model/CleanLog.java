package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 擦拭保养记录
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_clean_log")
public class CleanLog implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 保养原因
     */
    private String resaon;

    /**
     * 擦拭(保养)时间
     */
    private String cleanDate;

    /**
     * 申请人id
     */
    private String applyPersonId;

    /**
     * 申请人
     */
    private String applyPerson;

    /**
     * 审批人
     */
    private String auditingPerson;

    /**
     * 审批人id
     */
    private Integer auditingPersonId;

    /**
     * 枪支数量
     */
    private Integer firearmCount;

    /**
     * 保养不通过原因
     */
    private String auditingReason;

    /**
     * 擦拭包养人id
     */
    private String cleanPerson1Id;

    /**
     * 擦拭保养人
     */
    private String cleanPerson1;

    /**
     * 擦拭包养人id
     */
    private String cleanPerson2Id;

    /**
     * 擦拭保养人
     */
    private String cleanPerson2;

    /**
     * 擦拭完成人
     */
    private String cleanCompletePerson1;

    /**
     * 擦拭完成人id
     */
    private Integer cleanCompletePerson1Id;

    /**
     * 擦拭完成人2
     */
    private String cleanCompletePerson2;

    /**
     * 擦拭完成人2id
     */
    private Integer cleanCompletePerson2Id;

    /**
     * 保养状态(01:待审批05:待保养10:审批不通过15:保养中20:待入柜25:保养完成30:保养取消)
     */
    private String status;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
