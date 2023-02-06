package com.jingde.equipment.app.cabinets.vo;

import java.util.List;

public class AreaVO {
	
	private String doorId;
	 //区域id
    private  String areaId;
   
	 //区域序号
    private  Integer areaIndex;
   
	 //区域排列方式
    private  String areaLayout;
   
	 //区域名字
    private  String areaName;
   
	 //区域高度
    private  Integer areaHeight;
   
	 //区域宽度
    private  Integer areaWidth;
    
    private List<SeatVO> seat;


	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Integer getAreaIndex() {
		return areaIndex;
	}

	public void setAreaIndex(Integer areaIndex) {
		this.areaIndex = areaIndex;
	}

	public String getAreaLayout() {
		return areaLayout;
	}

	public void setAreaLayout(String areaLayout) {
		this.areaLayout = areaLayout;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getAreaHeight() {
		return areaHeight;
	}

	public void setAreaHeight(Integer areaHeight) {
		this.areaHeight = areaHeight;
	}

	public Integer getAreaWidth() {
		return areaWidth;
	}

	public void setAreaWidth(Integer areaWidth) {
		this.areaWidth = areaWidth;
	}

	public List<SeatVO> getSeat() {
		return seat;
	}

	public void setSeat(List<SeatVO> seat) {
		this.seat = seat;
	}
    
	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}

	@Override
	public boolean equals(Object obj) {
		AreaVO s=(AreaVO)obj;
	return areaId.equals(s.areaId) && areaIndex.equals(s.areaIndex) 
			&& areaLayout.equals(s.areaLayout) && areaName.equals(s.areaName) 
			&& areaHeight.equals(s.areaHeight) && areaWidth.equals(s.areaWidth);
	}
	@Override
	public int hashCode() {
	String in = areaId + areaIndex +areaLayout+areaName+areaHeight+areaWidth;
	return in.hashCode();
	}
	
}
