package com.jingde.equipment.app.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.model.SignLog;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.app.security.dao.SignLogMapper;
import com.jingde.equipment.app.security.service.SignLogService;
import com.jingde.equipment.app.security.vo.SignLogVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by JingDe on 2019/03/12.
 *
 * @author
 */
@Service
@Transactional
public class SignLogServiceImpl extends ServiceImpl<SignLogMapper, SignLog> implements SignLogService {
    @Resource
    private SignLogMapper signLogMapper;

    @Override
    public Result findSignList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<SignLogVO> list = signLogMapper.findSignList(1);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public SignLogVO findSignById(Integer id) {
        return signLogMapper.findSignById(id);
    }
}
