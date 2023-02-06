package com.jingde.equipment.app.police.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.police.dto.PoliceAnnualInspectionDTO;
import com.jingde.equipment.model.PoliceAnnualInspection;
import com.jingde.equipment.app.police.vo.PoliceAnnualInspectionVO;

import java.util.List;

/**
 * @author
 */
public interface AnnualInspectionService extends IService<PoliceAnnualInspection> {

    List<PoliceAnnualInspectionVO> findByPage();

    void add(PoliceAnnualInspectionDTO form);

    PoliceAnnualInspectionVO findOneById(Integer id);

    List<PoliceAnnualInspectionVO> findByPoliceId(String policeId,String status);
}
