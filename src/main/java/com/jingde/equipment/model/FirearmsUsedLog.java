package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 领用审批登记表
 * </p>
 *
 * @author jingde
 * @since 2020-01-03
 */
@Data

@TableName("t_firearms_used_log")
public class FirearmsUsedLog {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 领用时间
     */
    private String applyDate;

    /**
     * 领用理由
     */
    private String applyReason;

    /**
     * 领用内容
     */
    private String applyContent;

    /**
     * 领枪人 id
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
     * 保管人 id
     */
    private Integer keepingPersonId;

    /**
     * 保管人
     */
    private String keepingPerson;

    /**
     * 保管人2 id
     */
    private Integer keepingPerson2Id;

    /**
     * 保管人
     */
    private String keepingPerson2;

    /**
     * 领枪确认人
     */
    private String confirmPerson;

    /**
     * 领枪确认人id
     */
    private Integer confirmPersonId;

    /**
     * 归还日期
     */
    private String returnDate;

    /**
     * 使用情况
     */
    private String usedNote;

    /**
     * 交枪人
     */
    private String returnPerson;

    /**
     * 交枪人 id
     */
    private Integer returnPersonId;

    /**
     * 备注
     */
    private String note;

    /**
     * 发枪日期
     */
    private Date distributeDate;

    /**
     * 是否跨区域：1.是 0.否
     */
    private String isInterRegion;

    /**
     * 预计交还时间
     */
    private String expectedReturnDate;

    /**
     * 延长交还时间
     */
    private String lengthenReturnDate;

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
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 保管人 id(还枪)
     */
    private Integer returnKeepingPersonId;

    /**
     * 保管人(还枪)
     */
    private String returnKeepingPerson;

    /**
     * 保管人2 id(还枪)
     */
    private Integer returnKeepingPerson2Id;

    /**
     * 保管人(还枪)
     */
    private String returnKeepingPerson2;
    /**
     *开启的枪座信息
     */
    private String ammoJson;


}
