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
 * 领用审批登记表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_firearms_apply_log")
public class FirearmsApplyLog implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
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
     * 状态：01 待审核 02 待领取 03 待还枪 04 已完成 05延时还枪 06待确认领取  07待确认还枪 08 待审核还枪  09 已取消
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
