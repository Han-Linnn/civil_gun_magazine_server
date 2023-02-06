package com.jingde.equipment.app.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.apply.dto.FirearmsApplyTypeLogDTO;
import com.jingde.equipment.model.AmmoReceiveBatchInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 弹药类型领取记录详情表 Mapper 接口
 * </p>
 *
 * @author jingde
 * @since 2020-07-15
 */
public interface AmmoReceiveBatchInfoMapper extends BaseMapper<AmmoReceiveBatchInfo> {

    void insertByList(@Param("list") List<FirearmsApplyTypeLogDTO> types);

    List<AmmoReceiveBatchInfo> selectByBatchId(@Param("batchId") Integer id);

}
