package com.jingde.equipment.app.apply.vo;

import lombok.Data;

@Data
public class FirearmsLogDetailVO {
    private Integer id;
    private String firearmType;
    private String firearmNo;
    private String receiveTime; //出库时间
    private String returnTime;  //入库时间
    private Integer status;  //状态:(0-在库,1-不在库,2-待保养,3-保养中,4-故障,...)
}
