package com.jingde.equipment.app.firearms.dto;

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
public class AmmoFirearmDTO {
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

}
