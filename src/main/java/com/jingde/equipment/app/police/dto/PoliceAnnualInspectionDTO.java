package com.jingde.equipment.app.police.dto;

/**
 * TPoliceAnnualInspection 实体类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2019-03-29 15:36:32
 * @version 1.0
 */

public class PoliceAnnualInspectionDTO {

	private Integer id;
	private String date;
	private String status;
	private String policeId;

	/** setter and getter method */
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return this.id;
	}
	public void setDate(String date){
		this.date = date;
	}
	public String getDate(){
		return this.date;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getStatus(){
		return this.status;
	}

	public String getPoliceId() {
		return policeId;
	}

	public void setPoliceId(String policeId) {
		this.policeId = policeId;
	}
}