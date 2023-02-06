package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * PoliceEquipmenlog 实体类
 * @author
 */
@Data
@TableName("t_police_equipment_log")
public class PoliceEquipmentLog {

	@TableId(type = IdType.AUTO)
	private Integer id;
	private String issueDate;
	private String firearmsType;
	private String firearmsNo;
	private String firearmsCode;
	private String managerStatus;
	private String usedLog;
	private String changeResult;
	private String note;
	private String managerPerson;
	private String auditingPerson;
	private String auditingDate;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	private Integer firearmsTypeId;

}