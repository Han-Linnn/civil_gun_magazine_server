package com.jingde.equipment.app.cabinets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.cabinets.dao.CabinetSeatLogMapper;
import com.jingde.equipment.model.CabinetOpenSeatLog;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 领枪卡座开锁记录 服务实现类
 * </p>
 *
 * @author jingde
 * @since 2019-09-10
 */
@Service
public class CabinetSeatLogService extends ServiceImpl<CabinetSeatLogMapper, CabinetOpenSeatLog> implements IService<CabinetOpenSeatLog> {
}
