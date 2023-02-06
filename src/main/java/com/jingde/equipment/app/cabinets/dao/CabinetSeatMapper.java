package com.jingde.equipment.app.cabinets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.cabinets.dto.CabinetSeatDTO;
import com.jingde.equipment.app.cabinets.vo.FirearmNoVO;
import com.jingde.equipment.app.cabinets.vo.LableVO;
import com.jingde.equipment.app.firearms.vo.FirearmsTypeVO;
import com.jingde.equipment.model.CabinetSeat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 柜子内部卡座 Mapper 接口
 * </p>
 *
 * @author jingde
 * @since 2019-09-11
 */
public interface CabinetSeatMapper extends BaseMapper<CabinetSeat> {
    /**
     * 获取所有枪号
     *
     * @return
     */
    List<FirearmNoVO> getFireArmNoList();

    List<FirearmNoVO> selectGunNoByCondition(@Param("gunType") String gunType, @Param("status")String status  );

    List<FirearmsTypeVO> availableList();

    List<CabinetSeatDTO> selectByCabinetId(@Param("cabinetId")String cabinetId, @Param("status")String status  );

    List<LableVO> selectFirearmTotal();

    List<LableVO> selectAmmoTotal();

}
