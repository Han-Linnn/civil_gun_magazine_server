package com.jingde.equipment.app.police.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.police.dto.PoliceAnnualInspectionDTO;
import com.jingde.equipment.app.police.service.AnnualInspectionService;
import com.jingde.equipment.app.police.vo.PoliceAnnualInspectionVO;
import com.jingde.equipment.app.system.dao.AnnualInspectionMapper;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.PoliceAnnualInspection;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AnnualInspectionServiceImpl extends ServiceImpl<AnnualInspectionMapper, PoliceAnnualInspection> implements AnnualInspectionService {

    private final AnnualInspectionMapper annualInspectionMapper;

    @Override
    public List<PoliceAnnualInspectionVO> findByPage() {
        return annualInspectionMapper.findByPage();
    }

    @Override
    public void add(PoliceAnnualInspectionDTO form) {
        if (StringUtils.isBlank(form.getStatus())) {
            throw new ServiceException("status为空");
        }
        if (StringUtils.isBlank(form.getPoliceId())) {
            throw new ServiceException("policeId为空");
        }
        if (StringUtils.isBlank(form.getDate())) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
            form.setDate(date);
        }
        annualInspectionMapper.add(form);

    }

    @Override
    public PoliceAnnualInspectionVO findOneById(Integer id) {
        if (null == id || 0 == id) {
            throw new ServiceException("id为空");
        }
        return annualInspectionMapper.findOneById(id);
    }

    @Override
    public List<PoliceAnnualInspectionVO> findByPoliceId(String policeId,String status) {
        return annualInspectionMapper.findByPoliceId(policeId,status);
    }
}
