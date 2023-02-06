package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * TPoliceUnhealthyRecord 实体类
 *
 * @author
 */
@Data
@TableName("t_police_unhealthy_record")
public class PoliceUnhealthyRecord {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String date;
    private String status;
    private String reason;
    private Integer policeId;
    private Integer useGunStatus;
    private java.util.Date createTime;
    private java.util.Date updateTime;

}