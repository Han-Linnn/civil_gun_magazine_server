package com.jingde.equipment.app.repair.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.repair.dao.CleanLogInfoMapper;
import com.jingde.equipment.app.repair.service.CleanLogInfoService;
import com.jingde.equipment.model.CleanLogInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CleanLogInfoServiceImpl extends ServiceImpl<CleanLogInfoMapper, CleanLogInfo> implements CleanLogInfoService {
    


}
