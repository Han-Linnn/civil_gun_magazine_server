package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @author
 */
@Data
@TableName("t_sign_log")
public class SignLog {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 制表单位
     */
    private String productDepartment;

    /**
     * 检查单位
     */
    private String checkDepartment;

    /**
     * 检查人员
     */
    private String checkName;

    /**
     * 检查日期
     */
    private String checkDate;

    /**
     * 被检单位
     */
    private String department;

    /**
     * 签名
     */
    private String signName;

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

    /**
     * 负责人
     */
    private String principal;

    /**
     * 存在问题
     */
    private String problem;

    /**
     * 提交时间
     */
    private String submitDate;

    /**
     * 上报单位
     */
    private String toDepartment;

    /**
     * html内容
     */
    private String content;

}