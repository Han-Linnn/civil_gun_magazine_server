package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 开锁领枪记录(卡座)
 * </p>
 *
 * @author jingde
 * @since 2019-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_cabinet_open_seat_log")
public class CabinetOpenSeatLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
