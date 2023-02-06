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
 * 柜子内部卡座
 * </p>
 *
 * @author jingde
 * @since 2019-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_cabinet_seat")
public class CabinetSeat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer cabinetId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 卡座类型
     */
    private String seatType;

    /**
     * 实体卡座ID
     */
    private String seatEntityId;

    /**
     * 实体卡座index
     */
    private String seatEntityIndex;

    /**
     * 枪支编号
     */
    private String firearmNo;

    /**
     * 枪支类型
     */
    private String firearmType;

    private Integer firearmTypeId;

    /**
     * 弹药类型
     */
    private String ammoType;

    private Integer ammoTypeId;

    /**
     * 弹药数量
     */
    private Integer ammoCount;

    /**
     * 是否可用：1 可用 0 不可用
     *  默认都为可用 （保留字段）
     */
    private Integer available;

    /**
     * 状态：0 未绑定枪支或弹药 1：已绑定&&在库 2：已绑定&&不在库 3：卡座故障
     */
    private Integer status;


}
