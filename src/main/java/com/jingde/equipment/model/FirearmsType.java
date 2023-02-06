package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 枪炮类型表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_firearms_type")
public class FirearmsType implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 枪炮名称
     */
    private String firearmsType;

    /**
     * 类型(0:枪支,1:炮)
     */
    private Integer type;

    /**
     * 登记日期
     */
    private String registerDate;

    /**
     * 登记人
     */
    private String registerPerson;

    /**
     * 枪支等级(:0:普通,1:特殊)==>保留字段
     */
    private Integer levelType;

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
