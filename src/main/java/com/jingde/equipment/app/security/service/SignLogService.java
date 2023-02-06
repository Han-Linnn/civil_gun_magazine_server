package com.jingde.equipment.app.security.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.model.SignLog;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.app.security.vo.SignLogVO;


/**
 * Created by JingDe on 2019/03/12.
 * @author
 */
public interface SignLogService extends IService<SignLog> {

	Result findSignList(Integer page, Integer size);

	SignLogVO findSignById(Integer id);
}
