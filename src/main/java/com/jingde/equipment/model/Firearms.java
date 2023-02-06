package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 枪支表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_firearms")
public class Firearms extends Model<Firearms> implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 枪炮类型id
     */
    private Integer firearmTypeId;

    /**
     * 枪支类型
     */
    private String firearmType;

    /**
     * 枪炮编号(枪炮,枪炮唯一码,枪炮二维码)
     */
    private String firearmNo;

    /**
     * 状态:(0-在库,1-不在库,2-待保养,3-保养中,4-故障,...)
     */
    private Integer status;

    /**
     * 预计归还时间(status ==1)
     */
    private String expectedReturnTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
