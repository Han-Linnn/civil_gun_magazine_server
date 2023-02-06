package com.jingde.equipment.app.cabinets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.Cabinet;
import com.jingde.equipment.model.Equipmencategory;
import com.jingde.equipment.app.system.vo.clean.FirearmsDataVO;
import com.jingde.equipment.app.system.vo.clean.ListDataVO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 *
 */
public interface CabinetTypeMapper extends BaseMapper<Equipmencategory> {

	List<Cabinet> cabinetSelect(@Param("status") Integer status);

	List<ListDataVO> cabinetFirearmsDeatils(@Param("codes") String[] cabinetCodes);

	List<FirearmsDataVO> firearmsDeatils(@Param("codes")String[] cabinetCodes);
	
}
