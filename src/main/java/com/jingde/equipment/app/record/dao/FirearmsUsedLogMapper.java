package com.jingde.equipment.app.record.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.record.vo.LableVo;
import com.jingde.equipment.model.FirearmsUsedLog;

import java.util.List;


/**
 * @author
 */
public interface FirearmsUsedLogMapper extends BaseMapper<FirearmsUsedLog> {

    List<LableVo> selectTotal();
}