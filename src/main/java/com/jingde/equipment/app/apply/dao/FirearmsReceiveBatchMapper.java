package com.jingde.equipment.app.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.apply.dto.FirearmsReceiveBatchDTO;
import com.jingde.equipment.app.apply.vo.FirearmsReceiveBatchVo;
import com.jingde.equipment.model.FirearmsReceiveBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请类型记录表 Mapper 接口
 *
 * @author termite
 * @since 2020-07-08
 */
public interface FirearmsReceiveBatchMapper extends BaseMapper<FirearmsReceiveBatch> {

    List<FirearmsReceiveBatchVo> selectByApplyId(@Param("applyId") Integer applyId);

    List<FirearmsReceiveBatchVo> selectByApplyIds(@Param("applyIds")List<Integer> applyIds);

    Integer insertByDto(@Param("dto")FirearmsReceiveBatchDTO dto);

    Integer selectMaxId();

    FirearmsReceiveBatchVo selectByBatchId(@Param("id")Integer id);
}
