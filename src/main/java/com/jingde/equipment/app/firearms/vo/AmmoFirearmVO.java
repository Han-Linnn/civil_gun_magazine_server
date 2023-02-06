package com.jingde.equipment.app.firearms.vo;

import lombok.Data;

/**
 * <p>
 * 弹药适用的枪支类型
 * </p>
 *
 * @author jingde
 * @since 2019-09-10
 */
@Data
public class AmmoFirearmVO {

    private Integer id;

    private Integer ammoId;

    private Integer firearmId;

    /**
     * 弹药类型
     */
    private String ammoType;

    /**
     * 枪支类型
     */
    private String firearmType;

    /**
     * 弹药数量
     */
    private Integer number;
    /**
     * 弹药类型(0:子弹,1:炮弹)
     */
    private Integer type;

}
