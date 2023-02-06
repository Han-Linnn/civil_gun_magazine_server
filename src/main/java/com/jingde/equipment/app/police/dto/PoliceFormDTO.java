package com.jingde.equipment.app.police.dto;

import com.jingde.equipment.annotation.ExcelAttribute;
import com.jingde.equipment.app.base.dto.PageDTO;
import lombok.Data;

/**
 * 警员表实体类:用来存放警员信息
 *
 * @author LEE.SIU.WAH
 * @version 1.0
 * @email lixiaohua7@163.com
 * @date 2019-03-07 16:15:53
 */
@Data
public class PoliceFormDTO extends PageDTO {

    //部门
    private String departmentName;
    //部门id
    private String departId;
    //职务
    private String postLevel;
    //警员名称
    private String policeName;
    //警员编号
    private String policeCode;
    //持枪证号
    private String firearmCode;
}