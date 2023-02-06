package com.jingde.equipment.app.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.apply.dto.FirearmsApplyTypeLogDTO;
import com.jingde.equipment.model.AmmoReturnBatchInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 弹药类型归还记录详情表 Mapper 接口
 * </p>
 *
 * @author jingde
 * @since 2020-07-15
 */
public interface AmmoReturnBatchInfoMapper extends BaseMapper<AmmoReturnBatchInfo> {

    void insertByList(@Param("list") List<FirearmsApplyTypeLogDTO> types);

    List<AmmoReturnBatchInfo> selectByBatchId(@Param("batchId")Integer id);
}
