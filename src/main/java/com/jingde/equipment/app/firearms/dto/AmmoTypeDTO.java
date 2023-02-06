package com.jingde.equipment.app.firearms.dto;

import lombok.*;

import java.util.List;

/**
 * Created by Goldrepo on 2019/2/28
 */
@Data
public class AmmoTypeDTO {

    private Integer id;

    //弹药类型
    private String ammoType;

    //数量
    private Integer number;

    //登记日期
    private String registerDate;

    //登记人
    private String registerPerson;

    //状态：1-有效，0-无效
    private String status;

    // 备注
    private String note;

    // 适用枪支
    private List<AmmoFirearmDTO> ammoFirearmList;

}
