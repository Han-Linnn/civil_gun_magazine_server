package com.jingde.equipment.app.cabinets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.cabinets.dao.CabinetSeatMapper;
import com.jingde.equipment.app.cabinets.dto.CabinetSeatDTO;
import com.jingde.equipment.app.cabinets.vo.FirearmNoVO;
import com.jingde.equipment.app.cabinets.vo.LableVO;
import com.jingde.equipment.app.cabinets.vo.TotalVO;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.CabinetSeat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 柜子内部卡座 服务实现类
 * </p>
 *
 * @author jingde
 * @since 2019-09-11
 */
@Service
public class CabinetSeatService extends ServiceImpl<CabinetSeatMapper, CabinetSeat> implements IService<CabinetSeat> {

    @Resource
    private CabinetSeatMapper cabinetSeatMapper;

    /**
     * 获取所有枪号
     *
     * @return
     */
    public List<FirearmNoVO> getFireArmNoList() {

        return cabinetSeatMapper.getFireArmNoList();
    }

    public List<FirearmNoVO> getFireArmNoListByType(String gunType) {
        List<FirearmNoVO> firearmNoVOS = cabinetSeatMapper.selectGunNoByCondition(gunType, "1");
        Map<String, List<String>> map = new HashMap<>();
        for (FirearmNoVO vo : firearmNoVOS) {
            List<String> list = map.get(vo.getFirearmType());
            if (null == list || list.size() == 0) {
                list = new ArrayList<>();
                list.add(vo.getFirearmNo());
                map.put(vo.getFirearmType(), list);
            } else {
                list.add(vo.getFirearmNo());
                map.put(vo.getFirearmType(), list);
            }
        }
        List<FirearmNoVO> res = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            FirearmNoVO vo = new FirearmNoVO();
            vo.setFirearmType(entry.getKey());
            vo.setList(entry.getValue());
            res.add(vo);
        }
        return res;
    }

    public List<CabinetSeatDTO> selectByCabinetId(String cabinetId, String status) {
        if (StringUtils.isBlank(cabinetId))
            throw new ServiceException("请选择枪柜");
        if (StringUtils.isBlank(status))
            status = "1";

        return cabinetSeatMapper.selectByCabinetId(cabinetId, status);
    }

    public List<TotalVO> selectTotalByType() {
        List<TotalVO> totalVOS = new ArrayList<>();
        List<LableVO> lableVOS = cabinetSeatMapper.selectAmmoTotal();
        TotalVO ammTotal = new TotalVO();
        int aCount = 0;
        if(null != lableVOS && 0 < lableVOS.size()) {
            for ( LableVO a: lableVOS) {
                aCount += a.getCount();
            }
        }
        ammTotal.setCount(aCount);
        ammTotal.setList(lableVOS);
        ammTotal.setType("弹药");
        totalVOS.add(ammTotal);

        List<LableVO> lableVOS1 = cabinetSeatMapper.selectFirearmTotal();
        TotalVO firTotal = new TotalVO();
        int fCount = 0;
        if (null != lableVOS1 && 0 < lableVOS1.size()) {
            for (LableVO f : lableVOS1) {
                fCount += f.getCount() ;
            }
        }
        firTotal.setCount(fCount);
        firTotal.setList(lableVOS1);
        firTotal.setType("枪支");
        totalVOS.add(firTotal);
        return totalVOS;
    }

}
