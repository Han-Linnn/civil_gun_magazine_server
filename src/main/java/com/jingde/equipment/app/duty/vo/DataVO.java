package com.jingde.equipment.app.duty.vo;

import lombok.Data;

@Data
public class DataVO {

	private Integer key; //枪炮id
	private Integer type; //类型
	private String value; //类型名称
	private Integer[] gun;
	private Integer[] bullet;
	private Integer tmpGun;
	private Integer tmpBullet;


}
