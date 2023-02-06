package com.jingde.equipment.app.apply.vo;

import lombok.Data;

import java.util.List;

/**
 * 申请类型记录
 *
 * @author termite
 * @since 2020-07-09
 */
@Data
public class FirearmsApplyTypeLogVo {

    private Integer id;

    /**
     * 申请记录id
     */
    private Integer applyId;

    /**
     * 枪炮类型id
     */
    private Integer firearmTypeId;

    /**
     * 枪炮类型(名称)
     */
    private String firearmType;

    /**
     * 类型(0:枪支/子弹,1:炮/炮弹)
     */
    private Integer type;

    /**
     * 弹药类型id
     */
    private Integer ammoTypeId;

    /**
     * 弹药名称
     */
    private String ammoType;


    /**
     * 申请枪支数量
     */
    private Integer applyFirearmCount;

    /**
     * 已领取枪炮数量
     */
    private Integer receivedFirearmCount;

    /**
     * 已归还枪炮数量
     */
    private Integer returnedFirearmCount;

    /**
     * 申请弹药数量
     */
    private Integer applyAmmoCount;

    /**
     * 已领取弹药数量
     */
    private Integer receivedAmmoCount;

    /**
     * 已归还弹药数量
     */
    private Integer returnedAmmoCount;

    /**
     * 领取的枪支详情
     */
    private List<FirearmsApplyTypeInfoVo> infos;


}
