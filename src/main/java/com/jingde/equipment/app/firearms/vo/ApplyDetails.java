package com.jingde.equipment.app.firearms.vo;

import lombok.Data;

/**
 * @Description
 * @auther XC
 * @create 2020-07-15 15:05
 */
@Data
public class ApplyDetails {

	private Integer id;
	private Integer firearmTypeId;
	private Integer type;
	private Integer count;
	private Integer receivedCount;
	private Integer returnedCount;
}
