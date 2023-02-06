package com.jingde.equipment.app.department.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * DepartmentDTO 实体类
 * 部门表单接收
 */
@Data
public class DepartmentDTO {

	private Integer id;
	@NotNull(message = "departmentName不能为空")
	private String departmentName;
	private String note;
	private String status;

}