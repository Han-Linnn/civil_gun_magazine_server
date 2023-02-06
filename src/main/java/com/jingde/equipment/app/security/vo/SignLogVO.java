package com.jingde.equipment.app.security.vo;

public class SignLogVO {

	private Integer id;
	//检查单位
	private String inspectUnit;
	//检查人员
	private String inspectors;
	//检查时间
	private String inspectDate;
	//被检单位
	private String beInspectUnit;
	//备件单位负责人签名
	private String personInCharge;
	//存在问题描述
	private String description;
	//整改情况上交日期
	private String rectificationDate;
	//上报单位
	private String reportUnit;
	//内容
	private String list;
	//制表单位
	private String productUnit;

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInspectUnit() {
		return inspectUnit;
	}

	public void setInspectUnit(String inspectUnit) {
		this.inspectUnit = inspectUnit;
	}

	public String getInspectors() {
		return inspectors;
	}

	public void setInspectors(String inspectors) {
		this.inspectors = inspectors;
	}

	public String getInspectDate() {
		return inspectDate;
	}

	public void setInspectDate(String inspectDate) {
		this.inspectDate = inspectDate;
	}

	public String getBeInspectUnit() {
		return beInspectUnit;
	}

	public void setBeInspectUnit(String beInspectUnit) {
		this.beInspectUnit = beInspectUnit;
	}

	public String getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRectificationDate() {
		return rectificationDate;
	}

	public void setRectificationDate(String rectificationDate) {
		this.rectificationDate = rectificationDate;
	}

	public String getReportUnit() {
		return reportUnit;
	}

	public void setReportUnit(String reportUnit) {
		this.reportUnit = reportUnit;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}
}
