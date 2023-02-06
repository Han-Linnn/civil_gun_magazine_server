package com.jingde.equipment.app.cabinets.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class SeatVO {
	 //枪座/弹座id
    private  String seatId;
    
    private String areaId;
   
	 //枪座/弹座排列方式
    private  String seatLayout;
   
	 //枪座/弹座名字
    private  String seatName;
   
	 //枪座/弹座高度
    private  Integer seatHeight;
   
	 //枪座/弹座宽度
    private  Integer seatWidth;
    
    private Integer seatIndex;
    
    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String seatCode;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String cardCode;
	private String inStatus;
	private String usedStatus;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String imgUrl;
	private String status;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private Integer firearmsTypeId;
	@JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
	private String firearmsType;
	private String seatType;

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getSeatLayout() {
		return seatLayout;
	}

	public void setSeatLayout(String seatLayout) {
		this.seatLayout = seatLayout;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public Integer getSeatHeight() {
		return seatHeight;
	}

	public void setSeatHeight(Integer seatHeight) {
		this.seatHeight = seatHeight;
	}

	public Integer getSeatWidth() {
		return seatWidth;
	}

	public void setSeatWidth(Integer seatWidth) {
		this.seatWidth = seatWidth;
	}

	public Integer getSeatIndex() {
		return seatIndex;
	}

	public void setSeatIndex(Integer seatIndex) {
		this.seatIndex = seatIndex;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getInStatus() {
		return inStatus;
	}

	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}

	public String getUsedStatus() {
		return usedStatus;
	}

	public void setUsedStatus(String usedStatus) {
		this.usedStatus = usedStatus;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getFirearmsTypeId() {
		return firearmsTypeId;
	}

	public void setFirearmsTypeId(Integer firearmsTypeId) {
		this.firearmsTypeId = firearmsTypeId;
	}

	public String getFirearmsType() {
		return firearmsType;
	}

	public void setFirearmsType(String firearmsType) {
		this.firearmsType = firearmsType;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	
}
