package com.jingde.equipment.app.cabinets.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by oceanover on 2019/9/12.
 */
@Data
public class CabinetSeatListDTO {
    private Integer cabinetId;

    /**
     * 枪柜id
     */
    private String cabinetEntityId;

    /**
     * 操作类型：0 普通开锁 1 取枪开锁 2 还枪开锁
     */
    private Integer actionType;

    /**
     * 枪柜编号
     */
    private String seqNo;

    /**
     * 是否需要保存领枪记录
     */
    private Integer needSaveLog;

    private List<CabinetSeatDTO> cabinetSeats;

    
}
