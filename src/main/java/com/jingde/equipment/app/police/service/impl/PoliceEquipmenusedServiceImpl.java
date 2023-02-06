package com.jingde.equipment.app.police.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.police.dao.PoliceEquipmenusedMapper;
import com.jingde.equipment.app.police.dao.PoliceMapper;
import com.jingde.equipment.app.police.dao.UnhealthyRecordMapper;
import com.jingde.equipment.app.police.dto.PoliceDTO;
import com.jingde.equipment.app.police.dto.PoliceUnhealthyRecordDTO;
import com.jingde.equipment.app.police.dto.StaffingDTO;
import com.jingde.equipment.app.police.service.PoliceEquipmenusedService;
import com.jingde.equipment.app.police.vo.PolicePageVO;
import com.jingde.equipment.model.Police;
import com.jingde.equipment.model.PoliceEquipmenused;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
public class PoliceEquipmenusedServiceImpl extends ServiceImpl<PoliceEquipmenusedMapper, PoliceEquipmenused> implements PoliceEquipmenusedService {

    private final PoliceEquipmenusedMapper policeEquipmenusedmapper;

    private final PoliceMapper policeMapper;

    private final UnhealthyRecordMapper unhealthyRecordMapper;

    @Override
    public PageInfo selectPageBytype(Integer type) {
        List<PolicePageVO> list = policeEquipmenusedmapper.selectPageBytype(type);
        return new PageInfo<>(list);
    }

    @Override
    public void saveOrUpdateBypoliceCode(StaffingDTO form) {

        PoliceEquipmenused model = new PoliceEquipmenused();
        BeanUtils.copyProperties(form, model);
        model.setStatus(Integer.parseInt(form.getType()));
        //1.警员持枪状态
        policeEquipmenusedmapper.saveOrUpdateBypoliceCode(model);
        //2.如果禁枪,添加不良记录
        //获取警员id
        Police police = policeMapper.selectByPoliceCode(form.getPoliceCode(), null);
        if ("0".equals(form.getType().trim())) { //禁枪,添加不良记录$修改禁枪状态

            //2.1警员不良记录操作
            PoliceUnhealthyRecordDTO un = new PoliceUnhealthyRecordDTO();
            un.setPoliceId(police.getId() + "");
            un.setReason(form.getReason());
            if (StringUtils.isBlank(form.getDate())) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
                form.setDate(date);
            }
            un.setDate(form.getDate());
            un.setUseGunStatus(1);
            //2.2 添加不良记录
            unhealthyRecordMapper.add(un);
        }
        //3.修改警员表(枪证号码,持枪状态)
        PoliceDTO dto = new PoliceDTO();
        dto.setId(police.getId()+"");
        policeMapper.updatePolice(dto);
    }

}
