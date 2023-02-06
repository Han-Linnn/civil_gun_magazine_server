package com.jingde.equipment.app.cabinets.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 开锁领枪记录
 * </p>
 *
 * @author jingde
 * @since 2019-09-23
 */
@Data
public class CabinetOpenLogVO implements Serializable {

    private Integer id;

    /**
     * 申请时间
     */
    private String applyDate;
    /**
     * 申请原因
     */
    private String applyReason;

    /**
     * 枪号
     */
    private String firearmNo;

    /**
     * 出库时间
     */
    private String deliveryTime;
    /**
     * 入库时间
     */
    private String storageTime;
    /**
     * 状态(0:未归还,1:已归还)
     */
    private Integer status;


}
