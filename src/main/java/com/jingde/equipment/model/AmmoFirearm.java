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
 * 弹药适用的枪支/炮类型
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_ammo_firearm")
public class AmmoFirearm implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 弹药id
     */
    private Integer ammoId;

    /**
     * 枪炮类型id
     */
    private Integer firearmId;

    /**
     * 弹药类型
     */
    private String ammoType;

    /**
     * 枪支类型
     */
    private String firearmType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
