package com.jingde.equipment.app.record.dto;

import com.jingde.equipment.app.base.dto.PageDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by oceanover on 2019-03-18.
 *
 * @author
 */
@Data
public class LogListDTO extends PageDTO {
    /**
     * 申请状态:0 -申请中, 1-审批通过(审批人签名), 2-用枪中(保管员签名领枪完毕), 3-还枪中, 4-已完成(保管员签名还枪完毕)
     */
    private List<String> status;
    /**
     * 用户状态(0:查询所有(默认),1查询自己)
     */
    private String userStatus;
    /**
     * 用户id
     */
    private List<Integer> userIds;
    /**
     * 部门状态:0:查询所有(默认),1:查询本部门
     */
    private String departmentStatus;

    /**
     * 时间状态:0:查询所有时间,1:查询当天申请领用的工单
     */
    private String dateStatus;
    /**
     * 查询延迟枪标识  (0:查询所有(默认),1:查询延迟)
     */
    private String delay;
}
