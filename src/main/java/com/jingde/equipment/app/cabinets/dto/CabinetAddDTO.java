package com.jingde.equipment.app.cabinets.dto;

import lombok.*;

@Data
public class CabinetAddDTO {
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
     * 状态：1-有效，0-无效
     */
    private Integer status;
    /**
     * 是否常用柜(0:常用,1不是常用)
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
}
