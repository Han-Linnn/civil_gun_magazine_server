package com.jingde.equipment.app.firearms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.model.AmmoFirearm;
import com.jingde.equipment.app.firearms.dao.AmmoFirearmMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 弹药适用的枪支类型 服务实现类
 * </p>
 *
 * @author jingde
 * @since 2019-09-10
 */
@Service
public class AmmoFirearmService extends ServiceImpl<AmmoFirearmMapper, AmmoFirearm> implements IService<AmmoFirearm> {
}
