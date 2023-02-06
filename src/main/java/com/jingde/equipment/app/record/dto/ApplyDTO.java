package com.jingde.equipment.app.record.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 装备申领
 * @author
 */
@Data
public class ApplyDTO {
    // 领用时间
    @NotNull(message = "领用时间(applyDate)不能为空")
    private String applyDate;
    // 领用理由
    @NotNull(message = "领用事由(applyReason)不能为空")
    private String applyReason;
    //是否跨区域：1.是 0.否
    private  String isInterRegion;
    //预计交还时间
    private String expectedReturnDate;
    //申领详细
    @NotNull(message = "申领详情集合(info)不能为空")
    private List<FirearmsUsedLogInfoDTO> info;

}
