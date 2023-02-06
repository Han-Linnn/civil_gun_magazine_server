package com.jingde.equipment.app.firearms.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Goldrepo on 2019/2/28
 */
@Data
public class AmmoChangeLogDTO {
    @NotNull(message = "registerDate不能为空")
    private String registerDate;
    private String registerName;

    private String cause;

    private Integer total;
    private String content;

    private String id; //变更记录id
    private Integer typeId; //类型id
    private String type;
    private Integer count;
    private Integer changeNumber;
    private Integer add; //增加
    private Integer reduce; //减少


}
