package com.jingde.equipment.app.firearms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.firearms.dto.AmmoChangeLogDTO;
import com.jingde.equipment.model.AmmoChangeLog;
import com.jingde.equipment.app.cabinets.vo.TypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface AmmoChangeLogMapper extends BaseMapper<AmmoChangeLog> {
    List<TypeVO> selectRegistrationChange();

    TypeVO selectMaxId();

    void saveLog(@Param("condition") AmmoChangeLogDTO condition);

    void updateContentbyId(@Param("condition") AmmoChangeLogDTO condition);

    TypeVO findById(@Param("id") String id);
}
