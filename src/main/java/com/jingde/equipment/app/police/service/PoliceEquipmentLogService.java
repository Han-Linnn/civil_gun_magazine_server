package com.jingde.equipment.app.police.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.app.firearms.dto.FirearmsEquipmentDTO;
import com.jingde.equipment.model.PoliceEquipmentLog;
import com.jingde.equipment.app.police.vo.PoliceEquipmenlogVO;

import java.util.List;

/**
 * @author
 */
public interface PoliceEquipmentLogService extends IService<PoliceEquipmentLog> {

    void insert(FirearmsEquipmentDTO form);

    void auditing(FirearmsEquipmentDTO form);

	Result findPoliceEquipmenLogList(Integer page, Integer size);

	PoliceEquipmenlogVO findPoliceEquipmenLogById(Integer id);

	List<String> getfirearmsNoList();
}
