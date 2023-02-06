package com.jingde.equipment.app.firearms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.cabinets.vo.RegisterListVO;
import com.jingde.equipment.app.firearms.dto.FirearmsChangeLogDTO;
import com.jingde.equipment.model.FirearmsChangeLog;

/**
 * @author
 */
public interface FirearmsChangeLogService extends IService<FirearmsChangeLog> {

    RegisterListVO select();

    void saveChangelog(FirearmsChangeLogDTO form);

    void updateChangelog(FirearmsChangeLogDTO form);
}
