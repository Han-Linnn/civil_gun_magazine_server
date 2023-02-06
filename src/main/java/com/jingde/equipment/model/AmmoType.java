package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 弹药类型表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_ammo_type")
public class AmmoType implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 弹药名称
     */
    private String ammoType;

    /**
     * 类型((0:子弹,1:炮弹))
     */
    private Integer type;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 登记日期
     */
    private String registerDate;

    /**
     * 登记人
     */
    private String registerPerson;

    /**
     * 状态：1-有效，0-无效
     */
    private Integer status;

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
