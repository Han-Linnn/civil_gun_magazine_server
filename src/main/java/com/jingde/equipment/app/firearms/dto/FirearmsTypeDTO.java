package com.jingde.equipment.app.firearms.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Created by Goldrepo on 2019/2/28
 */
@Data
public class FirearmsTypeDTO {
    private Integer id;
    //枪支类型
    @NotNull(message = "firearmsType不能为空")
    private String firearmsType;
    //备注
    private String note;

    //枪支等级
    private String levelType;

    // 枪炮类型
	@NotNull(message = "type不能为空")
	private Integer type;
}
