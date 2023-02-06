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
 * 审批日志表
 * </p>
 *
 * @author jingde
 * @since 2019-09-10
 */
@Data
@TableName("t_approval_log")
public class ApprovalLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请记录
     */
    private String firearmsUsedLogId;
    /**
     * 审批人id
     */
    private String approvalPersonId;
    /**
     * 审批时间
     */
    private String approvalDate;
    /**
     * 登录账号id
     */
    private String loginAccountId;
    /**
     * 登录账号ip
     */
    private String loginIp;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
