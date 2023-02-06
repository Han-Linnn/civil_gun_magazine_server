package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色菜单中间表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_group_menu")
public class GroupMenu implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * (角色/组)id
     */
    private Integer groupId;

    /**
     * 权限url
     */
    private String permissionUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
