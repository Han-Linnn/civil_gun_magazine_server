package com.jingde.equipment.app.record.vo;

import com.jingde.equipment.model.FirearmsUsedLog;
import com.jingde.equipment.model.FirearmsUsedLogInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Data
public class FirearmsUsedLogInfoVo extends FirearmsUsedLogInfo {
    private Integer id;
    private Integer logId;
    private String gunType;
    private String gunNo;
    private Integer bullet;
    private Integer leftCout;
    private Integer ammoCount;
    private Integer status;

}