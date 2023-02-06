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
 * 授权子账号日志表
 * </p>
 *
 * @author jingde
 * @since 2020-01-19
 */
@Data
@TableName("t_authorize_log")
public class AuthorizeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 授权警员编号
     */
    private String authorizePoliceCode;

    /**
     * 授权用户id
     */
    private Integer authorizeId;

    /**
     * 授权用户的子账号id
     */
    private Integer subId;
    /**
     * 授权警员编号
     */
    private String UserPoliceCode;

    /**
     * 用户id(被授权人id)
     */
    private Integer userId;

    /**
     * 启用(0:启用,1:关闭启用)
     */
    private Integer startUsing;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
