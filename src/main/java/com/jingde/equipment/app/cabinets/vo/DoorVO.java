package com.jingde.equipment.app.cabinets.vo;

import java.util.List;

public class DoorVO {
	 //门id
    private  String doorId;
   
	 //门序号
    private  Integer doorIndex;
   
	 //门排列方式
    private  String doorLayout;
   
	 //门名字
    private  String doorName;
   
	 //门高度
    private  Integer doorHeight;
   
	 //门宽度
    private  Integer doorWidth;
    
    private List<AreaVO> area;

	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}

	public Integer getDoorIndex() {
		return doorIndex;
	}

	public void setDoorIndex(Integer doorIndex) {
		this.doorIndex = doorIndex;
	}

	public String getDoorLayout() {
		return doorLayout;
	}

	public void setDoorLayout(String doorLayout) {
		this.doorLayout = doorLayout;
	}

	public String getDoorName() {
		return doorName;
	}

	public void setDoorName(String doorName) {
		this.doorName = doorName;
	}

	public Integer getDoorHeight() {
		return doorHeight;
	}

	public void setDoorHeight(Integer doorHeight) {
		this.doorHeight = doorHeight;
	}

	public Integer getDoorWidth() {
		return doorWidth;
	}

	public void setDoorWidth(Integer doorWidth) {
		this.doorWidth = doorWidth;
	}

	public List<AreaVO> getArea() {
		return area;
	}

	public void setArea(List<AreaVO> area) {
		this.area = area;
	}	
	
	@Override
	public boolean equals(Object obj) {
		DoorVO s=(DoorVO)obj;
	return doorId.equals(s.doorId) && doorIndex.equals(s.doorIndex) 
			&& doorLayout.equals(s.doorLayout) && doorName.equals(s.doorName) 
			&& doorHeight.equals(s.doorHeight) && doorWidth.equals(s.doorWidth);
	}
	@Override
	public int hashCode() {
	String in = doorId + doorIndex +doorLayout+doorName+doorHeight+doorWidth;
	return in.hashCode();
	}
	
}
