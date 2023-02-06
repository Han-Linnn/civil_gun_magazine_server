package com.jingde.equipment.app.record.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.ApprovalLog;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审批日志 Mapper 接口
 * </p>
 *
 * @author jingde
 * @since 2019-09-27
 */
public interface ApprovalLogMapper extends BaseMapper<ApprovalLog> {
    
    void save(@Param("form") ApprovalLog log);
}
