package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 申请领用详细表
 * </p>
 *
 * @author jingde
 * @since 2019-12-26
 */
@Data
@TableName("t_firearms_used_log_info")
public class FirearmsUsedLogInfo {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申领id
     */
    private Integer logId;

    /**
     * 柜子id
     */
    private Integer cabinetId;

    /**
     * 实体卡座id
     */
    private String seatEntityId;

    /**
     * 卡座类型
     */
    private String seatType;

    /**
     * 枪型
     */
    private String gunType;

    /**
     * 枪号
     */
    private String gunNo;

    /**
     * 枪支申领数量
     */
    private Integer gunNum;

    /**
     * 申领子弹
     */
    private Integer bullet;

    /**
     * 子弹剩余数量(还枪剩余子弹数量)
     */
    private Integer leftCout;

    /**
     * 枪支状态:0=正常,1=异常
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
