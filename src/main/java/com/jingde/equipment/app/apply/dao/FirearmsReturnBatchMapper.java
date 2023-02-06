package com.jingde.equipment.app.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.apply.dto.FirearmsReturnBatchDTO;
import com.jingde.equipment.app.apply.vo.FirearmsReturnBatchVo;
import com.jingde.equipment.model.FirearmsReturnBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请类型记录表 Mapper 接口
 *
 * @author termite
 * @since 2020-07-08
 */
public interface FirearmsReturnBatchMapper extends BaseMapper<FirearmsReturnBatch> {

    List<FirearmsReturnBatchVo> selectByApplyId(@Param("applyId") Integer applyId);

    List<FirearmsReturnBatchVo> selectByApplyIds(@Param("list")List<Integer> applyIds);

    Integer insertByDto(@Param("dto") FirearmsReturnBatchDTO dto);

    Integer selectMaxId();

    FirearmsReturnBatchVo selectByBatchId(@Param("batchId")Integer id);
}
