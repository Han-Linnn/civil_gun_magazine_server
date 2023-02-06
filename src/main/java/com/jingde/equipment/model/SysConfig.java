package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * SysConfig 实体类
 * @author
 */
@Data
@TableName("t_sys_config")
public class SysConfig {

	@TableId(type = IdType.AUTO)
	private Integer id;
	//系统名称
	private String name;
	//logo
	private String logo;
	//版本信息
	private String copyright;
	//技术支持
	private String technology;
	//服务电话
	private String mobile;

}