package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("t_clean_log_info")
public class CleanLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
