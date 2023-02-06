package com.jingde.equipment.app.apply.dto;

import com.jingde.equipment.app.base.dto.PageDTO;
import lombok.Data;

@Data
public class FirearmsApplyLogDTO extends PageDTO {

    private Integer id;
    private String startDate;
    private String endDate;
    private String status;

}
