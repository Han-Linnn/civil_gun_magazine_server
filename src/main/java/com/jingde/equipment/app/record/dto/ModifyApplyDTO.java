package com.jingde.equipment.app.record.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 装备申领
 *
 * @author
 */
@Data
public class ModifyApplyDTO {
    // 申请记录 id
    @NotNull(message = "id不能为空")
    private Integer id;
    // 领用时间
    private String applyDate;
    // 领用理由
    private String applyReason;
    // 枪型
    private String gunType;
    // 枪号
    private String gunNo;
    // 申领子弹
    private String bullet;
    // 归还时间
    private String returnDate;
    // 使用情况
    private String usedNote;
    // 交还子弹
    private String leftCout;
    //是否跨区域：1.是 0.否
    private  String isInterRegion;
    //预计交还时间
    private String expectedReturnDate;
    //申领的详情
    private List<FirearmsUsedLogInfoDTO> info;
}
