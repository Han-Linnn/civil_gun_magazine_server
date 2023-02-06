package com.jingde.equipment.app.police.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.police.dto.PoliceUnhealthyRecordDTO;
import com.jingde.equipment.model.PoliceUnhealthyRecord;
import com.jingde.equipment.app.police.vo.PoliceUnhealthyRecordVO;

import java.util.List;

/**
 * @author
 */
public interface UnhealthyRecordService extends IService<PoliceUnhealthyRecord> {

    List<PoliceUnhealthyRecordVO> findByPage(Integer policeId);

    void add(PoliceUnhealthyRecordDTO form);

    PoliceUnhealthyRecordVO findOneById(Integer id);

    List<PoliceUnhealthyRecordVO> findByPoliceId(String policeId,String reason);
}
