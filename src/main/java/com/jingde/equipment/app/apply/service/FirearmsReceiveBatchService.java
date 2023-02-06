package com.jingde.equipment.app.apply.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.apply.vo.FirearmsReceiveBatchVo;
import com.jingde.equipment.model.FirearmsReceiveBatch;

/**
 * <p>
 * 申请类型记录表 服务类
 * </p>
 *
 * @author jingde
 * @since 2020-07-08
 */
public interface FirearmsReceiveBatchService extends IService<FirearmsReceiveBatch> {

    FirearmsReceiveBatchVo findListById(Integer id);
}
