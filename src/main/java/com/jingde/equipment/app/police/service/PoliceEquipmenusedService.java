package com.jingde.equipment.app.police.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.police.dto.StaffingDTO;
import com.jingde.equipment.model.PoliceEquipmenused;


/**
 * @author
 */
public interface PoliceEquipmenusedService extends IService<PoliceEquipmenused> {

    PageInfo selectPageBytype(Integer type);

    void saveOrUpdateBypoliceCode(StaffingDTO form);

}
