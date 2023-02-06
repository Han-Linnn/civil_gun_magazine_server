package com.jingde.equipment.app.cabinets.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.cabinets.dto.CabinetAddDTO;
import org.apache.ibatis.annotations.Param;

import com.jingde.equipment.app.cabinets.dto.CabinetDetailDTO;
import com.jingde.equipment.model.Cabinet;
import com.jingde.equipment.model.CabinetDetail;

/**
 * @author
 */
public interface CabinetMapper extends BaseMapper<Cabinet> {

    /**
     * 查询枪柜列表
     *
     * @return
     */
    List<Cabinet> cabinetList();

    /**
     * 详细的枪柜列表（包含枪支信息）
     *
     * @return
     */
    List<Map> detailCabinetList();

    /**
     * 详细的枪柜列表（包含弹药信息）
     *
     * @return
     */
    List<Map> detailWithAmmoCabinetList();

    /**
     * 查询枪柜详情
     *
     * @return
     */
    Cabinet cabinetBycabinetCode(@Param("condition") CabinetAddDTO condition);

    /**
     * 查询枪柜下面的枪座列表
     *
     * @return
     */
    List<CabinetDetail> cabinetDetailList(@Param("condition") CabinetAddDTO condition);

    /**
     * 新增枪柜
     *
     * @param condition
     */
    void insertCabinet(@Param("condition") CabinetAddDTO condition);

    /**
     * 新增枪座
     *
     * @param condition
     */
    void insertCabinetSeat(@Param("condition") CabinetDetailDTO condition);

    /**
     * 枪座详情
     *
     * @param condition
     * @return
     */
    CabinetDetail cabinetDetail(@Param("condition") CabinetDetailDTO condition);

    /**
     * 修改枪座
     *
     * @param condition
     */
    void updateCabinetSeat(@Param("condition") CabinetDetailDTO condition);

    /**
     * 删除枪柜
     *
     * @param condition
     */
    void deleteCabinet(@Param("condition") CabinetAddDTO condition);

    /**
     * 删除该枪柜对应的枪座
     *
     * @param condition
     */
    void deleteCabinetSeat(@Param("condition") CabinetAddDTO condition);

    /**
     * 查询枪柜详情
     *
     * @return
     */
    Cabinet cabinetById(@Param("condition") CabinetAddDTO condition);

    List<Cabinet> cabinetSelect(@Param("type") Integer type);
}
