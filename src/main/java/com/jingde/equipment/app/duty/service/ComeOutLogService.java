package com.jingde.equipment.app.duty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.model.ComeOutlog;

/**
 * @author
 */
public interface ComeOutLogService extends IService<ComeOutlog> {
	Result findComeOutList(Integer page, Integer size);
}
