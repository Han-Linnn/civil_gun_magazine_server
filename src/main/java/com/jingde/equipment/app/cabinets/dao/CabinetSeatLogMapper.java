package com.jingde.equipment.app.cabinets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.CabinetOpenSeatLog;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 开锁领枪记录 Mapper 接口
 * </p>
 *
 * @author jingde
 * @since 2019-09-23
 */
public interface CabinetSeatLogMapper extends BaseMapper<CabinetOpenSeatLog> {
    /**
     * 更新领用记录里枪支的归还状态
     *
     * @param seatEntityId
     * @param status
     */
    void updateBackStatus(@Param("seatEntityId") String seatEntityId, @Param("status") Integer status);
}
