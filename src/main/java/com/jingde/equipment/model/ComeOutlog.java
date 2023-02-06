package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author
 */
@Data
@TableName("t_come_out_log")
public class ComeOutlog {

	@TableId(type = IdType.AUTO)
	private Integer id;
	private String policeName;
	private String job;
	private String department;
	private Integer partnerCount;
	private String reason;
	private String comeOutDate;
	private String comeOutResult;
	private String note;
	private String registerDate;
	private String registerName;
	private java.util.Date createTime;
	private java.util.Date updateTime;

}