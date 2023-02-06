package com.jingde.equipment.app.apply.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.apply.dao.FirearmsApplyTypeInfoMapper;
import com.jingde.equipment.app.apply.service.FirearmsApplyTypeInfoService;
import com.jingde.equipment.model.FirearmsApplyTypeInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class FirearmsApplyTypeInfoServiceImpl extends ServiceImpl<FirearmsApplyTypeInfoMapper, FirearmsApplyTypeInfo> implements FirearmsApplyTypeInfoService {

}
