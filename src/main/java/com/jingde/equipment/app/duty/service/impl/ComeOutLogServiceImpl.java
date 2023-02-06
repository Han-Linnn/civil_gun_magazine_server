package com.jingde.equipment.app.duty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.duty.service.ComeOutLogService;
import com.jingde.equipment.app.duty.vo.ComeOutlogVO;
import com.jingde.equipment.app.system.dao.ComeOutLogMapper;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.ComeOutlog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ComeOutLogServiceImpl extends ServiceImpl<ComeOutLogMapper, ComeOutlog> implements ComeOutLogService {

    private final ComeOutLogMapper comeOutLogMapper;

    @Override
    public Result findComeOutList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<ComeOutlogVO> list = comeOutLogMapper.findComeOutList();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
