package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 职务表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_post")
public class Post implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 职务名称
     */
    private String postName;

    /**
     * 直接上级职务id
     */
    private Integer parentPostId;

    /**
     * 直接上级职务名称
     */
    private String parentPostName;

    /**
     * 职务级别(保留字段)
     */
    private Integer postLevel;

    /**
     * 状态：1-有效，0-无效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
