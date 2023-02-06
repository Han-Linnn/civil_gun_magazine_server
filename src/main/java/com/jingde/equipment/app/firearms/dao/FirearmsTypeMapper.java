package com.jingde.equipment.app.firearms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.duty.vo.FirearmsTypeTitleVO;
import com.jingde.equipment.app.firearms.vo.AmmoFirearmVO;
import com.jingde.equipment.app.firearms.vo.FirearmsTypeVO;
import com.jingde.equipment.app.firearms.vo.TypeTitleVO;
import com.jingde.equipment.model.FirearmsType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 枪支类型的mapper
 *
 * @author hjy
 * 2019-03-05
 */
public interface FirearmsTypeMapper extends BaseMapper<FirearmsType> {

    List<TypeTitleVO> findByStatus(@Param("status") String status);

    List<FirearmsTypeTitleVO> firearmsTypeTitle(@Param("status") Integer status);

    /**
     * 枪支类型列表
     *
     * @return
     */
    List<FirearmsTypeVO> firearmsTypeList();

    List<AmmoFirearmVO> findFirearmAmmoType(@Param("firearmId")Integer firearmId);

    List<FirearmsTypeVO> selectListByCondition(@Param("type")Integer type, @Param("firearmsType")String firearmsType);
}
