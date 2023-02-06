package com.jingde.equipment.app.user.dto;


import lombok.Data;

/**
 * user form表单
 * @author
 * @email
 * @date 2019-03-19 09:46:14
 * @version 1.0
 */
@Data
public class UserDTO {

	private String id;
	private String account;
	private String password;
	private String policeId;
	private String policeCode;
	private String policeName;
	private String officeId;
	private String newPassword;
	private String oldPassword;
	private Integer isSubAccount;// 是否有子账号(0:有,1:没有(默认是1))
	private String subAccount;  //子账号 (不填是==>s+警员编号)
	private String subPassword; //子账号密码 (不填跟主账号一致)
	private String postLevel;//职务级别(0:部门正职,1:部门副职,2:普通职员)
	private Integer isSon;//是否有子账号(0:有子账号,1:没有子账号(默认))
	//子账号是否启用(0:启用,1:不启用)
	private Integer enableSubAccount;
	//是否局长(0:不是,1:局长)
	private Integer isCommissioner;
	private String homePage;
	private String subAccountId;

	
}