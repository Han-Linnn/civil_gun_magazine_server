package com.jingde.equipment.app.cabinets.dto;

import java.util.List;

public class CabinetAreaDTO {
	private String id ;
	private String index;
	private String layout;
	private String name;
	private String height;
	private String width;
	private List<SeatDTO> shortGunSeat;
	private List<SeatDTO> longGunSeat;
	private List<SeatDTO> bulletDrawerSeat;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public List<SeatDTO> getShortGunSeat() {
		return shortGunSeat;
	}
	public void setShortGunSeat(List<SeatDTO> shortGunSeat) {
		this.shortGunSeat = shortGunSeat;
	}
	public List<SeatDTO> getLongGunSeat() {
		return longGunSeat;
	}
	public void setLongGunSeat(List<SeatDTO> longGunSeat) {
		this.longGunSeat = longGunSeat;
	}
	public List<SeatDTO> getBulletDrawerSeat() {
		return bulletDrawerSeat;
	}
	public void setBulletDrawerSeat(List<SeatDTO> bulletDrawerSeat) {
		this.bulletDrawerSeat = bulletDrawerSeat;
	}
	
	
}
