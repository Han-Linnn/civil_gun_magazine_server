package com.jingde.equipment.app.cabinets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.cabinets.dao.CabinetOpenLogMapper;
import com.jingde.equipment.model.CabinetOpenLog;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 领枪开锁记录 服务实现类
 * </p>
 *
 * @author jingde
 * @since 2019-09-10
 */
@Service
public class CabinetOpenLogService extends ServiceImpl<CabinetOpenLogMapper, CabinetOpenLog> implements IService<CabinetOpenLog> {
}
