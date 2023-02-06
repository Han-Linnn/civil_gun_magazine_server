package com.jingde.equipment.app.firearms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.firearms.dto.FirearmsChangeLogDTO;
import com.jingde.equipment.app.firearms.vo.TypeTitleVO;
import com.jingde.equipment.model.FirearmsChangeLog;
import com.jingde.equipment.app.cabinets.vo.TypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface FirearmsChangeLogMapper extends BaseMapper<FirearmsChangeLog> {

    List<TypeTitleVO> findByStatus(@Param("status")String status);

    List<TypeVO> selectRegistrationChange();

    TypeVO selectMaxId();

    void saveLog(@Param("condition") FirearmsChangeLogDTO condition);

    void updateContentbyId(@Param("condition") FirearmsChangeLogDTO condition);

    TypeVO findById(@Param("id")String id);
}
