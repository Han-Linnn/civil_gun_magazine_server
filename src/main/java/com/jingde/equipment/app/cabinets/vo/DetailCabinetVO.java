package com.jingde.equipment.app.cabinets.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 柜子信息(包含枪支弹药信息)
 * </p>
 *
 * @author jingde
 * @since 2019-09-11
 */
@Data
public class DetailCabinetVO {

    private Integer id;

    /**
     * 柜子名称
     */
    private String cabinetName;

    /**
     * 柜子类型：0 枪柜 1 弹柜 2 综合柜
     */
    private Integer cabinetType;

    /**
     * 柜子序号
     */
    private String seqNo;

    /**
     * 备注
     */
    private String note;

    /**
     * 所属IP
     */
    private String ip;

    /**
     * 柜子动态码
     */
    private String cabinetCode;

    /**
     * 枪柜id
     */
    private String cabinetEntityId;

}
