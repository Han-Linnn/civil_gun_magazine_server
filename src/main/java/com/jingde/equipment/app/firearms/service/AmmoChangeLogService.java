package com.jingde.equipment.app.firearms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.firearms.dto.AmmoChangeLogDTO;
import com.jingde.equipment.model.AmmoChangeLog;
import com.jingde.equipment.app.cabinets.vo.RegisterListVO;

/**
 * @author
 */
public interface AmmoChangeLogService extends IService<AmmoChangeLog> {

    RegisterListVO select();

    void saveChangelog(AmmoChangeLogDTO form);

    void updateChangelog(AmmoChangeLogDTO form);
}
