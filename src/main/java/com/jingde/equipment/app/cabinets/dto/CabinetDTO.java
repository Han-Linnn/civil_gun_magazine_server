package com.jingde.equipment.app.cabinets.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Goldrepo on 2019/2/28
 */
@Data
public class CabinetDTO {
    private Integer id;

    //子类型
    private String equipmentCategory;

    //所属: 0-枪，1-弹
    private String type;

    //备注
    private String note;

    //登记日期
    private String registerDate;

    //登记人
    private String registerPerson;

    //创建时间
    private String createTime;

    //更新时间
    private String updateTime;

    //柜子id
    private String cabinetId;

    //是否全选(0:是(默认),1不是)
    private String allElection;
    
    //枪支详情
    private List<CabinetSeatDTO> info;


}
