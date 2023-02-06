package com.jingde.equipment.app.repair.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 擦拭保养记录详情
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data

public class CleanLogInfoDTO  {

    private Integer id;

    /**
     * 保养记录id
     */
    private Integer cleanLogId;

    /**
     * 枪炮类型id
     */
    private Integer firearmTypeId;

    /**
     * 枪支类型
     */
    private String firearmType;

    /**
     * 枪支编号(XX,XX,XX)
     */
    private String firearmNo;

    /**
     * 是否全选(0:全选所有的枪支)
     */
    private String status;

}
