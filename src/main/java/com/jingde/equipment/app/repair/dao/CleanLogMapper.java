package com.jingde.equipment.app.repair.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.repair.dto.CleanLogDTO;
import com.jingde.equipment.app.system.vo.clean.CleanLogVO;
import com.jingde.equipment.model.CleanLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface CleanLogMapper extends BaseMapper<CleanLog> {

	List<CleanLogVO> findCleanLogList();

	String cleanStatusById(@Param("id") Integer id);

	void updateStatusById(@Param("dto") CleanLogDTO dto);
}
