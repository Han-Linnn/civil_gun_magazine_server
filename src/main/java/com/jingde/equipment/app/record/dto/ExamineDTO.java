package com.jingde.equipment.app.record.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by oceanover on 2019-03-10.
 * 审批
 *
 * @author
 */
@Data
public class ExamineDTO {

    private Integer id;
    private List<Integer> ids;
    // 审批人
    private String auditingPerson;
    // 审批人 id
    private Integer auditingPersonId;
    // 密码
    private String password;
}
