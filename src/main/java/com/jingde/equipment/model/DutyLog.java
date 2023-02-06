package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * @author
 */
@Data
@TableName("t_duty_log")
public class DutyLog {

	@TableId(type = IdType.AUTO)
	private Integer id;
	private String dutyDate;
	private String dutyContent;
	private String offDutyPersonId;//交班人1id
	private String offDutyPerson;//交班人1
	private String offDutyPerson2Id;//交班人2id
	private String offDutyPerson2;//交班人2
	private String onDutyPersonId;//接班人1id
	private String onDutyPerson;//接班人1
	private String onDutyPerson2Id;//接班人2id
	private String onDutyPerson2;//接班人2
	private String note;
	private java.util.Date createTime;
	private java.util.Date updateTime;

}