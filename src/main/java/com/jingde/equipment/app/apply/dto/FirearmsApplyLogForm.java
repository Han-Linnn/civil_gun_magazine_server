package com.jingde.equipment.app.apply.dto;

import lombok.Data;

import java.util.List;

@Data
public class FirearmsApplyLogForm {

    private Integer id;
    private String applyDate;
    private String applyReason;
    private Integer applyPersonId;
    private String applyPerson;
    private Integer auditingPersonId;
    private String auditingPerson;
    private String returnDate;
    private String status;
    private String note;
    private String auditingPassword;
    private List<FirearmsApplyTypeLogDTO> list;


}
