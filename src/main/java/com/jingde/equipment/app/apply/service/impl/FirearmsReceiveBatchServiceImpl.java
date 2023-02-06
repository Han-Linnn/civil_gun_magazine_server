package com.jingde.equipment.app.apply.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.apply.dao.AmmoReceiveBatchInfoMapper;
import com.jingde.equipment.app.apply.dao.FirearmsApplyTypeInfoMapper;
import com.jingde.equipment.app.apply.dao.FirearmsReceiveBatchMapper;
import com.jingde.equipment.app.apply.service.FirearmsReceiveBatchService;
import com.jingde.equipment.app.apply.vo.FirearmsApplyTypeInfoVo;
import com.jingde.equipment.app.apply.vo.FirearmsReceiveBatchVo;
import com.jingde.equipment.model.AmmoReceiveBatchInfo;
import com.jingde.equipment.model.FirearmsReceiveBatch;
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
public class FirearmsReceiveBatchServiceImpl extends ServiceImpl<FirearmsReceiveBatchMapper, FirearmsReceiveBatch> implements FirearmsReceiveBatchService {

    private final FirearmsReceiveBatchMapper firearmsReceiveBatchMapper;

    private final FirearmsApplyTypeInfoMapper firearmsApplyTypeInfoMapper;

    private final AmmoReceiveBatchInfoMapper ammoReceiveBatchInfoMapper;

    @Override
    public FirearmsReceiveBatchVo findListById(Integer id) {
        FirearmsReceiveBatchVo vo = firearmsReceiveBatchMapper.selectByBatchId(id);
        if (null == vo) {
            return new FirearmsReceiveBatchVo();
        }
        //查询该批次枪炮,弹药的领取详情
        List<FirearmsApplyTypeInfoVo> list = firearmsApplyTypeInfoMapper.selectByReceiveBatchId(id);
        vo.setFirearmsList(list);
        List<AmmoReceiveBatchInfo> ammoReceiveBatchInfos = ammoReceiveBatchInfoMapper.selectByBatchId(id);
        vo.setAmmoList(ammoReceiveBatchInfos);
        return vo;
    }
}
