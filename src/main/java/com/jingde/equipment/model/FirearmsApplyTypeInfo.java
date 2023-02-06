package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 申请类型记录表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_firearms_apply_type_info")
public class FirearmsApplyTypeInfo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请记录id
     */
    private Integer applyId;

    /**
     * 申请类型记录id
     */
    private Integer applyTypeId;

    /**
     * 枪炮类型id
     */
    private Integer firearmTypeId;

    /**
     * 枪炮类型(名称)
     */
    private String firearmType;

    /**
     * 枪炮编号(枪炮,枪炮唯一码,枪炮二维码)
     */
    private String firearmNo;

    /**
     * 领取批次id
     */
    private Integer receiveBatchId;

    /**
     * 归还批次id
     */
    private Integer returnBatchId;

    /**
     * 状态(0:未归还,1:已归还)
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
