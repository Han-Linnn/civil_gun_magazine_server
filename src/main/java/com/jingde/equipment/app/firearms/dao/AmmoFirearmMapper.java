package com.jingde.equipment.app.firearms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.firearms.vo.AmmoFirearmVO;
import com.jingde.equipment.model.AmmoFirearm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 弹药适用的枪支类型 Mapper 接口
 * </p>
 *
 * @author jingde
 * @since 2019-09-10
 */
public interface AmmoFirearmMapper extends BaseMapper<AmmoFirearm> {

    List<AmmoFirearmVO> selectList(@Param("type") Integer type, @Param("firearmsTypeId") Integer firearmsTypeId, @Param("ammoType") String ammoType);

    List<AmmoFirearmVO> selectListByIds(@Param("list")List<Integer> list);

    List<AmmoFirearm> listByAmmoId(@Param("ammoId")Integer id);
}
