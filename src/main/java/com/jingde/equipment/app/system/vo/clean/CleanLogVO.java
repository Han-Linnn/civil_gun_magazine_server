package com.jingde.equipment.app.system.vo.clean;

import java.util.List;

public class CleanLogVO {

	private Integer id;
	//申请人
	private String name;
	//创建日期
	private String date;
	//保养情况
	private String situation;
	//备注
	private String notes;
	//状态
	private String state;
	//枪支信息
	private List<ListDataVO> listDatumVOS;
	//枪支信息json
	private String listDataJson;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<ListDataVO> getListDatumVOS() {
		return listDatumVOS;
	}

	public void setListDatumVOS(List<ListDataVO> listDatumVOS) {
		this.listDatumVOS = listDatumVOS;
	}

	public String getListDataJson() {
		return listDataJson;
	}

	public void setListDataJson(String listDataJson) {
		this.listDataJson = listDataJson;
	}
}
