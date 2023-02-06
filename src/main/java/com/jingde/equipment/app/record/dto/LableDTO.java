package com.jingde.equipment.app.record.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class LableDTO {
    private String key;
    private String value;
    private String policeCode;
    
}
