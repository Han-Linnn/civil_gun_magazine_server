package com.jingde.equipment.app.police.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.firearms.dto.FirearmsEquipmentDTO;
import com.jingde.equipment.model.PoliceEquipmentLog;
import com.jingde.equipment.app.police.vo.PoliceEquipmenlogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface PoliceEquipmentLogMapper extends BaseMapper<PoliceEquipmentLog> {
    List<PoliceEquipmenlogVO> findPoliceEquipmenLogList();

    void save(@Param("condition") FirearmsEquipmentDTO condition);

	PoliceEquipmenlogVO findPoliceEquipmenLogById(@Param("id") Integer id);

	List<String> getfirearmsNoList();

	void auditingAll(@Param("condition") FirearmsEquipmentDTO condition);

	void auditing(@Param("condition") FirearmsEquipmentDTO condition);
}
