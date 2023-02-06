package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * SignItem 实体类
 *
 * @author
 */
@Data
@TableName("t_sign_item")
public class SignItem {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String itemLevel;
    private Integer parentId;
    private Integer itemSeq;
    private String itemName;
    private String itemContent;
    private String followRange;
    private java.util.Date createTime;
    private java.util.Date updateTime;

}