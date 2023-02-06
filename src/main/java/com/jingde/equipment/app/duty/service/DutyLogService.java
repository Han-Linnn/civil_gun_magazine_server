package com.jingde.equipment.app.duty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.duty.dto.DutyLogDTO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.model.DutyLog;

/**
 * @author
 */
public interface DutyLogService extends IService<DutyLog> {
     void updateNewest();

	Result onDutyLogList(Integer page, Integer size);

	Result lastDutyLog();

	void add(DutyLogDTO vo);

    void update(DutyLogDTO vo);
}
