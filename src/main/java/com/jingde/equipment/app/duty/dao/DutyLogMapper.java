package com.jingde.equipment.app.duty.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.DutyLog;
import com.jingde.equipment.app.duty.vo.DutyLogVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author
 */
public interface DutyLogMapper extends BaseMapper<DutyLog> {

    DutyLogVO selectMaxId();

    List<DutyLogVO> selectListByPage();

	List<DutyLogVO> onDutyLogList();

	DutyLogVO lastDutyLog();

	/**
	 * 根据枪支类型查询出对应的枪支数量
	 * @param firearmTypeId
	 * @return
	 */
	@Select("SELECT COUNT(id) FROM t_cabinet_seat WHERE `status` = 1 and firearm_type_id = #{firearmTypeId}")
	String findFirearmsTypeCount(@Param("firearmTypeId")String firearmTypeId);

	/**
	 * 根据弹药类型查询出对应子弹的数量
	 * @param firearmTypeId
	 * @return
	 */
	@Select("SELECT SUM(ammo_count) FROM t_cabinet_seat WHERE `status` = 1 and ammo_type_id in (SELECT ammo_id FROM t_ammo_firearm WHERE firearm_id = #{firearmTypeId})")
	String findAmmoTypeCount(@Param("firearmTypeId")String firearmTypeId);

}
