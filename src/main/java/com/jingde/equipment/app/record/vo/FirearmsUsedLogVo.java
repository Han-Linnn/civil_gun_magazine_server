package com.jingde.equipment.app.record.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jingde.equipment.model.FirearmsUsedLog;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Data
public class FirearmsUsedLogVo extends FirearmsUsedLog {
    private Integer id;
    // 领用时间
    private String applyDate;
    // 领用理由
    private String applyReason;
    // 领枪人
    private String applyPerson;
    // 审批人
    private String auditingPerson;
    // 审批时间
    private String auditingDate;
    // 保管人
    private String keepingPerson;
    // 归还日期
    private String returnDate;
    // 保管人
    private String keepingPerson2;
    // 交枪人
    private String returnPerson;
    // 备注
    private String note;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    // 领枪人 id
    private Integer applyPersonId;
    // 审批人 id
    private Integer auditingPersonId;
    // 保管人 id
    private Integer keepingPersonId;
    // 保管人2 id
    private Integer keepingPerson2Id;
    // 交枪人 id
    private Integer returnPersonId;
    // 发枪日期
    private Date distributeDate;
    //是否跨区域：1.是 0.否
    private  String isInterRegion;
    //预计交还时间
    private  String expectedReturnDate;
    //延长交还时间
    private  String lengthenReturnDate;
    //延时审批人id
    private  Integer delayApproverId;
    //延时审批人
    private  String delayApprover;
    // 状态：01 待审核 02 待领取 03 待还枪 04 已完成 05延时还枪 09 已取消
    private String status;

    private List<FirearmsUsedLogInfoVo> info;
}