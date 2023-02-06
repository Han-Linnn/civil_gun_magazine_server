package com.jingde.equipment.app.apply.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.apply.dao.FirearmsApplyTypeLogMapper;
import com.jingde.equipment.app.apply.service.FirearmsApplyTypeLogService;
import com.jingde.equipment.model.FirearmsApplyTypeLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 申请类型记录表 服务实现类
 *
 * @author termite
 * @since 2020-07-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FirearmsApplyTypeLogServiceImpl extends ServiceImpl<FirearmsApplyTypeLogMapper, FirearmsApplyTypeLog> implements FirearmsApplyTypeLogService {

}
