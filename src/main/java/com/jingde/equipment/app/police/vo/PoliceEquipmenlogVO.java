package com.jingde.equipment.app.police.vo;

public class PoliceEquipmenlogVO {

	private Integer id;
	//日期
	private String date;
	//枪型
	private String model;
	//枪型id
	private Integer modelId;
	//枪号
	private String gunNumber;
	//枪证号码
	private String credentialsNumber;
	//管理状态（1:登记，2:已审核）
	private String state;
	//痕迹建档
	private String trace;
	//变动情况
	private String condition;
	//备注
	private String remark;
	//枪管员
	private String administrator;
	//责任审核人
	private String verifier;

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getGunNumber() {
		return gunNumber;
	}

	public void setGunNumber(String gunNumber) {
		this.gunNumber = gunNumber;
	}

	public String getCredentialsNumber() {
		return credentialsNumber;
	}

	public void setCredentialsNumber(String credentialsNumber) {
		this.credentialsNumber = credentialsNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAdministrator() {
		return administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
}
