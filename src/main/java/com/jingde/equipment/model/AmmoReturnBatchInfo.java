package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 弹药类型归还记录详情表
 * </p>
 *
 * @author jingde
 * @since 2020-07-14
 */
@Data
@TableName("t_ammo_return_batch_info")
public class AmmoReturnBatchInfo implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 弹药类型id
     */
    private Integer ammoTypeId;

    /**
     * 弹药名称
     */
    private String ammoType;

    /**
     * 归还弹药数量
     */
    private Integer returnedAmmoCount;

    /**
     * 归还批次id
     */
    private Integer returnBatchId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
