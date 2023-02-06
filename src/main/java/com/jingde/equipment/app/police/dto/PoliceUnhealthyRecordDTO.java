package com.jingde.equipment.app.police.dto;

/**
 * TPoliceUnhealthyRecord 实体类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2019-03-29 15:36:32
 * @version 1.0
 */

public class PoliceUnhealthyRecordDTO {


	private Integer id;
	private String date;
	private String reason;
	private String policeId;
	private Integer useGunStatus;


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
	public void setReason(String reason){
		this.reason = reason;
	}
	public String getReason(){
		return this.reason;
	}
	public void setPoliceId(String policeId){
		this.policeId = policeId;
	}
	public String getPoliceId(){
		return this.policeId;
	}

	public Integer getUseGunStatus() {
		return useGunStatus;
	}

	public void setUseGunStatus(Integer useGunStatus) {
		this.useGunStatus = useGunStatus;
	}
}