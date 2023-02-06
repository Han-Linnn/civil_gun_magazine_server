package com.jingde.equipment.app.apply.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.apply.dao.AmmoReturnBatchInfoMapper;
import com.jingde.equipment.app.apply.dao.FirearmsApplyTypeInfoMapper;
import com.jingde.equipment.app.apply.dao.FirearmsReturnBatchMapper;
import com.jingde.equipment.app.apply.service.FirearmsReturnBatchService;
import com.jingde.equipment.app.apply.vo.FirearmsApplyTypeInfoVo;
import com.jingde.equipment.app.apply.vo.FirearmsReturnBatchVo;
import com.jingde.equipment.model.AmmoReturnBatchInfo;
import com.jingde.equipment.model.FirearmsReturnBatch;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 申请类型记录表 服务实现类
 * </p>
 *
 * @author jingde
 * @since 2020-07-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FirearmsReturnBatchServiceImpl extends ServiceImpl<FirearmsReturnBatchMapper, FirearmsReturnBatch> implements FirearmsReturnBatchService {

    private final FirearmsReturnBatchMapper firearmsReturnBatchMapper;

    private final FirearmsApplyTypeInfoMapper firearmsApplyTypeInfoMapper;

    private final AmmoReturnBatchInfoMapper ammoReturnBatchInfoMapper;

    @Override
    public FirearmsReturnBatchVo findListById(Integer id) {
        FirearmsReturnBatchVo vo = firearmsReturnBatchMapper.selectByBatchId(id);
        if (null == vo) {
            return new FirearmsReturnBatchVo();
        }
        //查询该批次枪炮,弹药的领取详情
        List<FirearmsApplyTypeInfoVo> list = firearmsApplyTypeInfoMapper.selectByReturnBatchId(id);
        vo.setFirearmsList(list);
        List<AmmoReturnBatchInfo> ammoList = ammoReturnBatchInfoMapper.selectByBatchId(id);
        vo.setAmmoList(ammoList);
        return vo;
    }
}
