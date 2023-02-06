package com.jingde.equipment.app.cabinets.vo;

import java.util.List;

/**
 *  枪柜详情的vo类
 * @author hjy
 *
 */
public class CabinetDetailVO {
	private String cabinetCode;//枪柜编号
	private String equipmentCategory;//枪柜类型
	private String status;//状态
	private String cabinetName;//柜子名称
	
	private String cabinetId;//实体柜子id
	 //枪柜序号
    private  Integer cabinetIndex;
   
	 //枪柜排列方式
    private  String cabinetLayout;
   
	 //枪柜高度
    private  Integer cabinetHeight;
   
	 //枪柜宽度
    private  Integer cabinetWidth;
	private List<DoorVO> door;//门列表
	
	public String getCabinetCode() {
		return cabinetCode;
	}
	public void setCabinetCode(String cabinetCode) {
		this.cabinetCode = cabinetCode;
	}
	public String getEquipmentCategory() {
		return equipmentCategory;
	}
	public void setEquipmentCategory(String equipmentCategory) {
		this.equipmentCategory = equipmentCategory;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<DoorVO> getDoor() {
		return door;
	}
	public void setDoor(List<DoorVO> door) {
		this.door = door;
	}
	public String getCabinetName() {
		return cabinetName;
	}
	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}
	public Integer getCabinetIndex() {
		return cabinetIndex;
	}
	public void setCabinetIndex(Integer cabinetIndex) {
		this.cabinetIndex = cabinetIndex;
	}
	public String getCabinetLayout() {
		return cabinetLayout;
	}
	public void setCabinetLayout(String cabinetLayout) {
		this.cabinetLayout = cabinetLayout;
	}
	public Integer getCabinetHeight() {
		return cabinetHeight;
	}
	public void setCabinetHeight(Integer cabinetHeight) {
		this.cabinetHeight = cabinetHeight;
	}
	public Integer getCabinetWidth() {
		return cabinetWidth;
	}
	public void setCabinetWidth(Integer cabinetWidth) {
		this.cabinetWidth = cabinetWidth;
	}
	public String getCabinetId() {
		return cabinetId;
	}
	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

}
