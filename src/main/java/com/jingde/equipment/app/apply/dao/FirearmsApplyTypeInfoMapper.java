package com.jingde.equipment.app.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.apply.dto.FirearmsApplyTypeInfoDTO;
import com.jingde.equipment.app.apply.vo.FirearmsApplyTypeInfoVo;
import com.jingde.equipment.model.FirearmsApplyTypeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请类型记录表 Mapper 接口
 *
 * @author termite
 * @since 2020-07-08
 */
public interface FirearmsApplyTypeInfoMapper extends BaseMapper<FirearmsApplyTypeInfo> {

    List<FirearmsApplyTypeInfoVo> selectByApplyId(@Param("applyId") Integer applyId);

    void insertByList(@Param("list")List<FirearmsApplyTypeInfoDTO> list);

    void updateListByIds(@Param("list")List<FirearmsApplyTypeInfoDTO> typeInfo);

    List<FirearmsApplyTypeInfoVo> selectByReceiveBatchId(@Param("batchId") Integer id);

    List<FirearmsApplyTypeInfoVo> selectByReturnBatchId(@Param("batchId") Integer id);
}
