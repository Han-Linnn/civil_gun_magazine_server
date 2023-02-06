package com.jingde.equipment.app.cabinets.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 开锁领枪记录(卡座)
 * </p>
 *
 * @author jingde
 * @since 2019-09-23
 */
@Data
public class CabinetSeatLogVO implements Serializable {

    private Integer id;

    private Integer openLogId;

    /**
     * 卡座类型
     */
    private String seatType;

    /**
     * 卡座名
     */
    private String seatName;

    /**
     * 实体卡座ID
     */
    private String seatEntityId;

    /**
     * 枪支类型
     */
    private String firearmType;

    /**
     * 枪支编号
     */
    private String firearmNo;

    /**
     * 弹药类型
     */
    private String ammoType;

    /**
     * 领用数量
     */
    private Integer ammoCount;

    /**
     * 归还状态（针对枪支）
     */
    private Integer backStatus;

    /**
     * 状态：0 未绑定枪支或弹药 1：已绑定&&在库 2：已绑定&&不在库
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
