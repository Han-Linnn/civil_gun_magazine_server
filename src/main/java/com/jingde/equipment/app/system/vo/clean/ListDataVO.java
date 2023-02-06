package com.jingde.equipment.app.system.vo.clean;

import java.util.List;

public class ListDataVO {

	private Integer id;
	//枪柜号
	private String cabinetCode;

	private List<FirearmsDataVO> firearmsDatumVOS;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCabinetCode() {
		return cabinetCode;
	}

	public void setCabinetCode(String cabinetCode) {
		this.cabinetCode = cabinetCode;
	}

	public List<FirearmsDataVO> getFirearmsDatumVOS() {
		return firearmsDatumVOS;
	}

	public void setFirearmsDatumVOS(List<FirearmsDataVO> firearmsDatumVOS) {
		this.firearmsDatumVOS = firearmsDatumVOS;
	}
}
