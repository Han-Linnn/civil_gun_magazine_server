package com.jingde.equipment.app.cabinets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.cabinets.dao.CabinetTypeMapper;
import com.jingde.equipment.model.Cabinet;
import com.jingde.equipment.model.Equipmencategory;
import com.jingde.equipment.app.system.vo.clean.FirearmsDataVO;
import com.jingde.equipment.app.system.vo.clean.ListDataVO;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Service
public class CabinetTypeService extends ServiceImpl<CabinetTypeMapper, Equipmencategory> implements IService<Equipmencategory> {

    @Resource
    private CabinetTypeMapper cabinetTypeMapper;

    public List<Cabinet> cabinetSelect() {
        return cabinetTypeMapper.cabinetSelect(1);
    }

    public List<ListDataVO> equipmentDetail(String[] cabinetCodes) {
        //枪柜列表
        List<ListDataVO> cabinets = cabinetTypeMapper.cabinetFirearmsDeatils(cabinetCodes);
        //枪柜枪支详情
        List<FirearmsDataVO> firearms = cabinetTypeMapper.firearmsDeatils(cabinetCodes);
        //将枪支与枪柜匹配
        for (ListDataVO ct : cabinets) {
            List<FirearmsDataVO> firearmsDatumVOS = new ArrayList<FirearmsDataVO>();
            for (FirearmsDataVO fa : firearms) {
                if (ct.getCabinetCode().equals(fa.getCabinetCode())) {
                    fa.setCabinetCode(null);
                    firearmsDatumVOS.add(fa);
                }
            }
            ct.setFirearmsDatumVOS(firearmsDatumVOS);
        }
        return cabinets;
    }
}
