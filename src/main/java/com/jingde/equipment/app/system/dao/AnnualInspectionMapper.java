package com.jingde.equipment.app.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.police.dto.PoliceAnnualInspectionDTO;
import com.jingde.equipment.model.PoliceAnnualInspection;
import com.jingde.equipment.app.police.vo.PoliceAnnualInspectionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 年检管理Mapper
 * @author hjy
 *
 */
public interface AnnualInspectionMapper extends BaseMapper<PoliceAnnualInspection> {

    List<PoliceAnnualInspectionVO> findByPage();

    void add(@Param("condition") PoliceAnnualInspectionDTO condition);

    PoliceAnnualInspectionVO findOneById(@Param("id")Integer id);

    List<PoliceAnnualInspectionVO> findByPoliceId(@Param("policeId") String policeId,@Param("status")String status);
}
