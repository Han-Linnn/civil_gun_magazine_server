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
    * 申领记录-枪支
    * </p>
*
* @author jingde
* @since 2019-09-27
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("t_firearms_gun_log")
    public class FirearmsGunLog implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer usedLogId;

    private Integer cabinetId;

            /**
            * 创建时间
            */
    private Date createTime;

            /**
            * 更新时间
            */
    private Date updateTime;

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
            * 枪支编号
            */
    private String firearmNo;

    private Integer firearmTypeId;

            /**
            * 弹药类型
            */
    private String ammoType;

    private Integer ammoTypeId;

            /**
            * 弹药数量
            */
    private Integer ammoCount;

            /**
            * 枪支状况：01 正常 | 02 异常 | 其他描述
            */
    private String state;


}
