package com.jingde.equipment.app.cabinets.dto;

import lombok.Data;

/**
 * <p>
 * 柜子内部卡座
 * </p>
 *
 * @author jingde
 * @since 2019-09-11
 */
@Data
public class CabinetSeatDTO {
    private Integer id;

    /**
     * 卡座id
     */
    private String seatId;
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
     * 枪支类型id
     */
    private String firearmTypeId;

    /**
     * 枪支编号
     */
    private String firearmNo;
    /**
     * 实体卡座index
     */
    private String seatEntityIndex;

    /**
     * 柜子id
     */
    private String cabinetId;

    /**
     * 柜子
     */
    private String cabinet;

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
     * 领用数量
     */
    private Integer takeCount;

    /**
     * 是否可用：1 可用 0 不可用
     */
    private Integer available;

    /**
     * 状态：0 未绑定枪支或弹药 1：已绑定&&在库 2：已绑定&&不在库 3: 故障
     */
    private Integer status;

}
