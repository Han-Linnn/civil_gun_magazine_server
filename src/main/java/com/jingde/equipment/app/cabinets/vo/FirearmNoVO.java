package com.jingde.equipment.app.cabinets.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 枪支信息（编号、、、）
 * </p>
 *
 * @author jingde
 * @since 2019-09-23
 */
@Data
public class FirearmNoVO implements Serializable {

    private Integer cabinetId;

    /**
     * 卡座类型
     */
    private String seatType;


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
     * 状态：0 未绑定枪支或弹药 1：已绑定&&在库 2：已绑定&&不在库
     */
    private Integer status;
    private List<String> list;

}
