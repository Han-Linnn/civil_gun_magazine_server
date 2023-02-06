package com.jingde.equipment.app.duty.dto;

import com.jingde.equipment.app.record.dto.LableDTO;
import lombok.Data;

import java.util.List;

@Data
public class HandoverDTO {
    private Integer id;
    private List<LableDTO> list;
}
