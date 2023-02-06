package com.jingde.equipment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 申请类型记录表
 * </p>
 *
 * @author jingde
 * @since 2020-07-07
 */
@Data
@TableName("t_firearms_return_batch")
public class FirearmsReturnBatch implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请记录id
     */
    private Integer applyId;

    /**
     * 保管人 id
     */
    private Integer keepingPersonId;

    /**
     * 保管人
     */
    private String keepingPerson;

    /**
     * 保管人2 id
     */
    private Integer keepingPerson2Id;

    /**
     * 保管人
     */
    private String keepingPerson2;

    /**
     * (还枪)确认人id
     */
    private Integer confirmPersonId;

    /**
     * (还枪)确认人
     */
    private String confirmPerson;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
