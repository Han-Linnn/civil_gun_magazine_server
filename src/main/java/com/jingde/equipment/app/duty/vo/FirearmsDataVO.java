package com.jingde.equipment.app.duty.vo;

import lombok.Data;

@Data
public class FirearmsDataVO {

	private Integer key; //枪炮id
	private Integer type; //类型
	private String value; //类型名称
	private String[] gun;
	private String[] bullet;

}
