package com.jingde.equipment.app.repair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.repair.dto.CleanLogDTO;
import com.jingde.equipment.app.repair.vo.CleanLogVo;
import com.jingde.equipment.model.CleanLog;
import com.jingde.equipment.model.User;

import java.util.List;

public interface CleanLogService extends IService<CleanLog> {

	/**
	 * 新增擦拭保养记录
	 *
	 * @param form
	 */
	void addCleanLog(User user,CleanLogDTO form);

	/**
	 * 擦拭保养编辑
	 *
	 * @param form
	 */
	void updateCleanLog(CleanLogDTO form);

	PageInfo listByPage(String status);

    void updateByAuditing(CleanLogDTO dto);

	String selectStatusById(Integer id);

    void updateStartById(CleanLogDTO form);

	CleanLogVo findInfoById(String id);
}
