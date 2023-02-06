package com.jingde.equipment.app.firearms.vo;

import lombok.*;

import java.util.List;

/**
 * @Description: 
 * @Title: AmmoTypeVO
 * @Auther: CzSix
 * @create 2019/3/8 11:16
 */

/*
 **/
@Data
public class AmmoTypeVO {
    //弹药类型id
    private Integer id;
    //弹药类型
    private String ammoType;
    private String ammoTypeId;
    private Integer type;
    private String registerDate;
    private String registerPerson;
    private Integer status;
    private String note;
    private Integer count;
    private String createTime;
    private String updateTime;
    // 适用的枪支
    private List<AmmoFirearmVO> ammoFirearmList;
    private Integer number;
}
