package com.jingde.equipment.app.cabinets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.cabinets.vo.CabinetOpenLogVO;
import com.jingde.equipment.model.CabinetOpenLog;

import java.util.List;

/**
 * <p>
 * 领枪记录 Mapper 接口
 * </p>
 *
 * @author jingde
 * @since 2019-09-23
 */
public interface CabinetOpenLogMapper extends BaseMapper<CabinetOpenLog> {
    /**
     * 枪支领用记录
     */
    List<CabinetOpenLogVO> cabinetOpenLogList();

}
