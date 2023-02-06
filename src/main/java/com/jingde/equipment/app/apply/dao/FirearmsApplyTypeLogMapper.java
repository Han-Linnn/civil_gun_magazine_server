package com.jingde.equipment.app.apply.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.apply.dto.FirearmsApplyTypeLogDTO;
import com.jingde.equipment.app.apply.vo.FirearmsApplyTypeLogVo;
import com.jingde.equipment.model.FirearmsApplyTypeLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请类型记录表 Mapper 接口
 *
 * @author termite
 * @since 2020-07-08
 */
public interface FirearmsApplyTypeLogMapper extends BaseMapper<FirearmsApplyTypeLog> {

    void insertByList(@Param("list") List<FirearmsApplyTypeLogDTO> list);

    void deleteByLogId(@Param("id") Integer id);

    List<FirearmsApplyTypeLogVo> selectByApplyId(@Param("applyId") Integer applyId);

    List<FirearmsApplyTypeLogVo> selectByApplyIds(@Param("list") List<Integer> applyIds);

    void updateReceivedCountByIds(@Param("list") List<FirearmsApplyTypeLogDTO> list);

    void updateReturnCountByIds(@Param("list") List<FirearmsApplyTypeLogDTO> typeInfo);
}
