package com.jingde.equipment.app.record.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 申请领用详细表
 * </p>
 *
 * @author jingde
 * @since 2019-12-25
 */
@Data
public class FirearmsUsedLogInfoDTO {

    private Integer id;
    private Integer logId;
    private String gunType;
    private String gunNo;
    private Integer bullet;
    private Integer cabinetId;
    private String seatEntityId;
    private String seatType;
    private Integer leftCout;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}
