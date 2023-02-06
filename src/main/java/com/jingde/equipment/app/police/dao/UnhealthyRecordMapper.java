package com.jingde.equipment.app.police.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.police.dto.PoliceUnhealthyRecordDTO;
import com.jingde.equipment.model.PoliceUnhealthyRecord;
import com.jingde.equipment.app.system.vo.PolicePageVO;
import com.jingde.equipment.app.police.vo.PoliceUnhealthyRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 年检管理Mapper
 * @author hjy
 *
 */
public interface UnhealthyRecordMapper extends BaseMapper<PoliceUnhealthyRecord> {

    List<PoliceUnhealthyRecordVO> findByPage(Integer policeid);

    void add(@Param("condition") PoliceUnhealthyRecordDTO condition);

    PoliceUnhealthyRecordVO findOneById(@Param("id") Integer id);

    List<PoliceUnhealthyRecordVO> findByPoliceId(@Param("policeId") String policeId,@Param("reason") String reason);

    List<PolicePageVO> selectByPage();
}
