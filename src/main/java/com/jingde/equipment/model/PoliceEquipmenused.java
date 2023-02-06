package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 配枪人员表实体类(禁枪/持枪)
 *
 * @author
 */
@Data
@TableName("t_police_equipment_used")
public class PoliceEquipmenused {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String policeCode;
    private String reason;
    private String note;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}