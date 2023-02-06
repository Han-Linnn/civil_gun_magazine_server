package com.jingde.equipment.app.system.vo;

import lombok.Data;

/**
 * 警员表实体类:用来存放警员信息
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2019-03-07 16:15:53
 * @version 1.0
 */
@Data
public class PoliceInfoVO {

	private Integer id;
	//警员民称
	private String policeName;
	//警员编号
	private String policeCode;
	//枪证号码
	private String firearmCode;
	//发证日期
	private String firearmCodePublish;
	//政治面貌
	private String politicalStatus;
	//民族
	private String nation;
	//头像
	private String photo;
	//手机号
	private String mobile;
	//手机短号
	private String mobileBak;
	//地址
	private String address;
	//性别(0-女，1-男)
	private String sex;
	//年龄
	private Integer age;
	//身份证
	private String idCard;

	//部门id
	private Integer departId;
	//部门名称
	private String departmentName;
	//年审情况(0:通过,1:不通过)
	private String annualVerification;
	//用枪状态
	private String useGunStatus;
	//枪号
	private String gunCode;
	//加入的权限组名称
	private String groupName;
	//账号类型0:主账号(默认),1:是子账号
	private String isSubAccount;
	//子账号 (不填是==>s+警员编号)
	private String subAccount;
	//职务id
	private Integer postId;
	//职务名称
	private String postName;
	//首页(0:警员首页,1:枪管员首页,2:领导首页)
    private String homePage;
	//是否有子账号==>0:有子账号,1:没有子账号(默认)
    private String isSon;


}