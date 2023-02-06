package com.jingde.equipment.app.firearms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.duty.vo.DataVO;
import com.jingde.equipment.app.firearms.vo.FirearmsTypeVO;
import com.jingde.equipment.model.Firearms;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FirearmsMapper extends BaseMapper<Firearms> {


	Integer updateFirearmsStatus(@Param("list") List<String> list,@Param("status") Integer status);

	void insertFirearmsList(@Param("firearmsList") List<Firearms> firearmsList);

	Integer updateFirearmsStatus(@Param("list") List<String> list,@Param("status") Integer status,@Param("time")String time);

    List<FirearmsTypeVO> selectByTimeAndId(@Param("time") String time, @Param("typeId")Integer typeId);

    List<Firearms> selectApplyList(@Param("time") String time, @Param("typeId")Integer typeId);

    Firearms findInfoByCode(@Param("qrCode")String qrCode);

    List<DataVO> selectTotal();
}
