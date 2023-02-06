package com.jingde.equipment.app.security.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.SignLog;
import com.jingde.equipment.app.security.vo.SignLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface SignLogMapper extends BaseMapper<SignLog> {

	List<SignLogVO> findSignList(@Param("status") Integer status);

	SignLogVO findSignById(@Param("id") Integer id);
}