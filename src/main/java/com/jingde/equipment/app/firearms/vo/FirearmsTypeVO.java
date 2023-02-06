package com.jingde.equipment.app.firearms.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 枪支类型表
 * </p>
 *
 * @author jingde
 * @since 2019-09-10
 */
@Data
public class FirearmsTypeVO {

    private Integer id;

    /**
     * 枪支类型
     */
    private String firearmsType;
    /**
     * 枪支类型id
     */
    private Integer firearmsTypeId;

    /**
     * 类型(0:枪支,1:炮)
     */
    private Integer type;

    /**
     * 登记日期
     */
    private String registerDate;

    /**
     * 登记人
     */
    private String registerPerson;

    /**
     * 状态：1-有效，0-无效
     */
    private Integer status;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    // 适用的枪支
    private List<AmmoFirearmVO> ammoFirearmList;

    private String levelType;

    // 总数
    private Integer sum;
    // 在库待领取
    private Integer noReceive;
    // 在库可申领
    private Integer canApply;
    // 出库数
    private Integer outStock;

    private List<TypeTitleVO> ammoList;

}
