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
 * 柜子信息表
 * </p>
 *
 * @author jingde
 * @since 2019-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_cabinet")
public class Cabinet implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 同步实体柜状态：1-已同步，0-未同步
     */
    private Integer syncStatus;

    /**
     * 备注
     */
    private String note;

    /**
     * 状态：1-有效，0-无效
     */
    private Integer status;

    /**
     * 是否常用柜(1:常用,0:不是常用)
     */
    private Integer isCommonly;

    /**
     * 登记(记录、修改)日期
     */
    private String registerDate;

    /**
     * 登记人
     */
    private String registerPerson;

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

    /**
     * 枪柜index
     */
    private Integer cabinetIndex;

    /**
     * 枪柜排列方式
     */
    private String cabinetLayout;

    /**
     * 枪柜高度
     */
    private Integer cabinetHeight;

    /**
     * 枪柜宽度
     */
    private Integer cabinetWidth;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
