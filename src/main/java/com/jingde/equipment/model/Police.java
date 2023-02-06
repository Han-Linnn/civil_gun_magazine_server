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
 * 警员表:用来存放警员信息
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_police")
public class Police implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门id
     */
    private Integer departId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 职务id
     */
    private Integer postId;

    /**
     * 职务名称
     */
    private String postName;

    /**
     * 姓名
     */
    private String policeName;

    /**
     * (警员/工作)编号
     */
    private String policeCode;

    /**
     * 政治面貌
     */
    private String politicalStatus;

    /**
     * 号码
     */
    private String mobile;

    /**
     * 备用号码
     */
    private String mobileBak;

    /**
     * 性别：0-女，1-男
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 民族
     */
    private String nation;

    /**
     * 照片
     */
    private String photo;

    /**
     * 住址
     */
    private String address;

    /**
     * 枪证(保留字段)
     */
    private String firearmCode;

    /**
     * 发证日期(保留字段)
     */
    private String firearmCodePublish;

    /**
     * 年审情况(0:通过,1:不通过)保留字段)
     */
    private String annualVerification;

    /**
     * 枪号(人员指定用枪)保留字段)
     */
    private String gunCode;

    /**
     * 用枪状态(0=允许用枪,1=禁止用枪)保留字段)
     */
    private Integer useGunStatus;

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
