package com.jingde.equipment.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 开锁领枪记录
 * </p>
 *
 * @author jingde
 * @since 2019-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_cabinet_open_log")
public class CabinetOpenLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer cabinetId;

    /**
     * 柜子序号
     */
    private String seqNo;

    /**
     * 枪柜id
     */
    private String cabinetEntityId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 操作类型：0 普通开锁 1 取枪开锁 2 还枪开锁
     */
    private Integer actionType;


}
