package com.jingde.equipment.app.record.dto;

import com.jingde.equipment.model.FirearmsUsedLog;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by oceanover on 2019-03-10.
 * 还枪
 * @author
 */
@Data
public class GiveBackDTO extends FirearmsUsedLog {
    @NotNull(message = "id不能为空")
    private Integer id;
    // 归还日期
    
    private String returnDate;
    // 交枪人
    private String returnPerson;
    // 交枪人ID
    private Integer returnPersonId;
    // 交枪人密码
    private String password;
    //申领的详情
    private List<FirearmsUsedLogInfoDTO> info;
    //保管人签名集合
    private List<LableDTO> list;
}
