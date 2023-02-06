package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表:用来存放登陆信息(建议拿警员编号作为登陆账号)
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_user")
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号(工作编号)
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 人员id
     */
    private Integer policeId;

    /**
     * (警员/工作)编号
     */
    private String policeCode;

    /**
     * 姓名
     */
    private String policeName;

    /**
     * 最后登陆时间
     */
    private String lastLoginTime;

    /**
     * 登陆次数
     */
    private Integer loginCount;

    /**
     * (0:主账号(默认),1:是子账号)
     */
    private Integer isSubAccount;

    /**
     * 是否有子账号(0:有子账号,1:没有子账号(默认))==>该账号如果是主账号
     */
    private Integer isSon;

    /**
     * 是否启用(0:启用,1:不启用)==>该账号如果是子账号
     */
    private Integer enableSubAccount;

    /**
     * 授权的子账号用户id(user.id)==>子账号id
     */
    private String subAccountId;

    /**
     * 首页(0:警员首页,1:枪管员首页,2:领导首页)
     */
    private String homePage;

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
