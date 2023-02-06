package com.jingde.equipment.app.police.vo;

/**
 * TPoliceAnnualInspection 实体类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2019-03-29 15:36:32
 * @version 1.0
 */

public class PoliceAnnualInspectionVO {


	private Integer id;
	private String date;
	private String status;
	private Integer policeId;
	private String policeCode;
	private String policeName;

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
	public void setPoliceId(Integer policeId){
		this.policeId = policeId;
	}
	public Integer getPoliceId(){
		return this.policeId;
	}

	public String getPoliceCode() {
		return policeCode;
	}

	public void setPoliceCode(String policeCode) {
		this.policeCode = policeCode;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}
}