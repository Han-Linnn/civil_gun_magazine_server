package com.jingde.equipment.app.apply.dto;

import lombok.Data;

/**
 * <p>
 * 申请类型记录表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
public class FirearmsApplyTypeInfoDTO {

    private Integer id;

    /**
     * 申请记录id
     */
    private Integer applyId;

    /**
     * 申请类型记录id
     */
    private Integer applyTypeId;

    /**
     * 枪炮类型id
     */
    private Integer firearmTypeId;

    /**
     * 枪炮类型(名称)
     */
    private String firearmType;

    /**
     * 枪炮编号(枪炮,枪炮唯一码,枪炮二维码)
     */
    private String firearmNo;

    /**
     * 领取批次id
     */
    private Integer receiveBatchId;

    /**
     * 归还批次id
     */
    private Integer returnBatchId;

    /**
     * 状态(0:未归还,1:已归还)
     */
    private String status;

}
