package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author
 */
@Data
@TableName("t_firearms_change_log")
public class FirearmsChangeLog {

	@TableId(type = IdType.AUTO)
	private Integer id;
	private String cause;
	private String total;
	private String content;
	private String registerDate;
	private String registerName;
	private java.util.Date createTime;
	private java.util.Date updateTime;

}