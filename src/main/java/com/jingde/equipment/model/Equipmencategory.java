package com.jingde.equipment.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author
 */
@Data
@TableName("t_equipment_category")
public class Equipmencategory {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String equipmentCategory;
    private String type;
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private String note;
    private String registerDate;
    private String registerPerson;
    private java.util.Date createTime;
    private java.util.Date updateTime;

}