package com.jingde.equipment.app.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.ComeOutlog;
import com.jingde.equipment.app.duty.vo.ComeOutlogVO;

import java.util.List;

/**
 * @author
 */
public interface ComeOutLogMapper extends BaseMapper<ComeOutlog> {

	List<ComeOutlogVO> findComeOutList();

}
