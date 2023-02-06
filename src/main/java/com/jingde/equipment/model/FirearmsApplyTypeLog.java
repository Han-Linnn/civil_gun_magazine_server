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
@TableName("t_firearms_apply_type_log")
public class FirearmsApplyTypeLog implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请记录id
     */
    private Integer applyId;

    /**
     * 枪炮类型id
     */
    private Integer firearmTypeId;

    /**
     * 枪炮类型(名称)
     */
    private String firearmType;

    /**
     * 弹药类型id
     */
    private Integer ammoTypeId;

    /**
     * 弹药名称
     */
    private String ammoType;

    /**
     * 申请枪支数量
     */
    private Integer applyFirearmCount;

    /**
     * 已领取枪炮数量
     */
    private Integer receivedFirearmCount;

    /**
     * 已归还枪炮数量
     */
    private Integer returnedFirearmCount;

    /**
     * 申请弹药数量
     */
    private Integer applyAmmoCount;

    /**
     * 已领取弹药数量
     */
    private Integer receivedAmmoCount;

    /**
     * 已归还弹药数量
     */
    private Integer returnedAmmoCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
