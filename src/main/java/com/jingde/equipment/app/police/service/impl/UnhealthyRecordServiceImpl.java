package com.jingde.equipment.app.police.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.police.dao.PoliceEquipmenusedMapper;
import com.jingde.equipment.app.police.dao.PoliceMapper;
import com.jingde.equipment.app.police.dao.UnhealthyRecordMapper;
import com.jingde.equipment.app.police.dto.PoliceUnhealthyRecordDTO;
import com.jingde.equipment.app.police.service.UnhealthyRecordService;
import com.jingde.equipment.app.police.vo.PoliceUnhealthyRecordVO;
import com.jingde.equipment.app.system.vo.PoliceInfoVO;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.PoliceEquipmenused;
import com.jingde.equipment.model.PoliceUnhealthyRecord;
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
public class UnhealthyRecordServiceImpl extends ServiceImpl<UnhealthyRecordMapper, PoliceUnhealthyRecord> implements UnhealthyRecordService {

    private final UnhealthyRecordMapper unhealthyRecordMapper;

    private final PoliceMapper PoliceMapper;

    private final PoliceEquipmenusedMapper policeEquipmenusedMapper;


    @Override
    public List<PoliceUnhealthyRecordVO> findByPage(Integer policeId) {
        return unhealthyRecordMapper.findByPage(policeId);
    }

    @Override
    public void add(PoliceUnhealthyRecordDTO form) {
        if (null == form.getUseGunStatus() || 1 != form.getUseGunStatus() ) {
            form.setUseGunStatus(0);
        }
        if (StringUtils.isBlank(form.getPoliceId())) {
            throw new ServiceException("policeId为空");
        }
        if (StringUtils.isBlank(form.getDate())) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
            form.setDate(date);
        }
        unhealthyRecordMapper.add(form);
        if (1 == form.getUseGunStatus()){
            //该不良记录导致警员被禁枪
            //t_police
            PoliceMapper.updateUseGunStatus(form.getPoliceId(),form.getUseGunStatus());
            //t_police_equipment_used
            //查询警员编号
            PoliceEquipmenused model = new PoliceEquipmenused();
            PoliceInfoVO info = PoliceMapper.findInfoById(Integer.valueOf(form.getPoliceId()));
            model.setPoliceCode(info.getPoliceCode());
            model.setStatus(0);
            model.setReason(form.getReason());
            policeEquipmenusedMapper.saveOrUpdateBypoliceCode(model);
        }
    }

    @Override
    public PoliceUnhealthyRecordVO findOneById(Integer id) {
        if (null == id || 0 == id) {
            throw new ServiceException("id为空");
        }
        return unhealthyRecordMapper.findOneById(id);
    }

    @Override
    public List<PoliceUnhealthyRecordVO> findByPoliceId(String policeId,String reason) {
        return unhealthyRecordMapper.findByPoliceId(policeId,reason);
    }
}
