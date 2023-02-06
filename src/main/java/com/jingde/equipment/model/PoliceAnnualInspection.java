package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * TPoliceAnnualInspection 实体类
 *
 * @author
 */
@Data
@TableName("t_police_annual_inspection")
public class PoliceAnnualInspection {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String date;
    private String status;
    private Integer policeId;
    private java.util.Date createTime;
    private java.util.Date updateTime;

}