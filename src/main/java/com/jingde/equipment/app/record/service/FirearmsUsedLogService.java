package com.jingde.equipment.app.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.record.dao.FirearmsUsedLogMapper;
import com.jingde.equipment.app.record.vo.LableVo;
import com.jingde.equipment.model.FirearmsUsedLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by oceanover on 2019/03/07.
 *
 * @author
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FirearmsUsedLogService extends ServiceImpl<FirearmsUsedLogMapper, FirearmsUsedLog> implements IService<FirearmsUsedLog> {

    @Resource
    private FirearmsUsedLogMapper firearmsUsedLogMapper;

    /**
     * 统计今天各个申领单个数
     *
     * @return
     */
    public List<LableVo> total() {
        return firearmsUsedLogMapper.selectTotal();
    }


}
