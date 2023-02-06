package com.jingde.equipment.app.duty.vo;

import lombok.Data;

import java.util.List;

/**
 * DutyLog 实体类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2019-03-07 16:15:53
 * @version 1.0
 */
@Data
public class DutyLogVO {

	private String id;
	private String dutyDate;
	private String dutyContent;
	private String offDutyPerson;//交班人1
	private String offDutyPerson2;//交班人2
	private String onDutyPerson;//接班人1
	private String onDutyPerson2;//接班人2
	private String note;
	private String createTime;
	private String updateTime;
	private String firearmsTitle; //枪支类型
	private String firearmsDutyContent; //枪支统计情况
	private String ammoTitle;
	private String ammoDutyContent; //弹药统计情况
    private String date;
    //备注
    private String notes;
    //数据
    private List<FirearmsDataVO> dataList;
    //统计情况
    private String content;


}