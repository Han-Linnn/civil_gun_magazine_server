package com.jingde.equipment.app.repair.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.cabinets.dto.CabinetSeatDTO;
import com.jingde.equipment.app.repair.dto.CleanLogInfoDTO;
import com.jingde.equipment.app.repair.vo.CleanLogInfoVo;
import com.jingde.equipment.model.CleanLogInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface CleanLogInfoMapper extends BaseMapper<CleanLogInfo> {


    void insertList(@Param("logId") Integer logId, @Param("list") List<CleanLogInfoDTO> list);

    List<CleanLogInfoVo> selectListMap(@Param("list")List<Integer> collect);

    List<CleanLogInfoVo> selectListByCleanLogId(@Param("cleanLogId") String id);
}
