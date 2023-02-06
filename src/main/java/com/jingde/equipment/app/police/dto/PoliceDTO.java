package com.jingde.equipment.app.police.dto;

import com.jingde.equipment.annotation.ExcelAttribute;
import com.jingde.equipment.app.base.dto.PageDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 警员表实体类:用来存放警员信息
 *
 * @author LEE.SIU.WAH
 * @version 1.0
 * @email lixiaohua7@163.com
 * @date 2019-03-07 16:15:53
 */
@Data
public class PoliceDTO extends PageDTO {

	// id
    private String id;
	// 职务名称
    @ExcelAttribute(sort = 0)
    private String policeName;
	// 工号
    @ExcelAttribute(sort = 1)
    private String policeCode;
	// 手机号码
    @ExcelAttribute(sort = 2)
    private String mobile;
	// 身份证号
    @ExcelAttribute(sort = 3)
    private String idCard;
	// 部门名称
    @ExcelAttribute(sort = 4)
    private String departmentName;
	// 职务名称
    @ExcelAttribute(sort = 5)
    private String postName; //职务名称
	// 性别
	@ExcelAttribute(sort = 6)
    private String sex;
	// 年龄
	@ExcelAttribute(sort = 7)
    private Integer age;
	// 民族
	@ExcelAttribute(sort = 8)
    private String nation;
	// 政治面貌
	@ExcelAttribute(sort = 9)
    private String politicalStatus;
	// 住址
	@ExcelAttribute(sort = 10)
    private String address;
	// 备用号码
	@ExcelAttribute(sort = 11)
    private String mobileBak;
	// 是否有子账号
	@ExcelAttribute(sort = 12)
    private String isSon;// 是否有子账号(0:有,1:没有(默认是1))

	@ExcelAttribute(sort = 13)
    private String subAccount;  //子账号 (不填是==>s+警员编号)

	@ExcelAttribute(sort = 14)
	private String subPassword; //子账号密码 (不填跟主账号一致)

	@ExcelAttribute(sort = 15)
    private String homePage; //首页(0:警员首页,1:枪管员首页,2:领导首页)

    private String departId;
    private String postId;
    private String photo;
    private String note;
    private String status;
    private String annualVerification;
    private String password;


}