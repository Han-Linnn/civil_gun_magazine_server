package com.jingde.equipment.app.police.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.firearms.dto.FirearmsEquipmentDTO;
import com.jingde.equipment.app.police.dao.PoliceEquipmentLogMapper;
import com.jingde.equipment.app.police.service.PoliceEquipmentLogService;
import com.jingde.equipment.app.police.vo.PoliceEquipmenlogVO;
import com.jingde.equipment.app.user.dao.UserMapper;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.PoliceEquipmentLog;
import com.jingde.equipment.model.User;
import com.jingde.equipment.util.Encrypt;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PoliceEquipmentLogServiceImpl extends ServiceImpl<PoliceEquipmentLogMapper, PoliceEquipmentLog> implements PoliceEquipmentLogService {

    private final PoliceEquipmentLogMapper policeEquipmentLogMapper;

    private final UserMapper userMapper;

    @Override
    public void insert(FirearmsEquipmentDTO form) {
        if (StringUtils.isBlank(form.getFirearmsType())) {
            throw new ServiceException("枪型为空");
        }
        if (StringUtils.isBlank(form.getFirearmsNo())) {
            throw new ServiceException("枪号为空");
        }
        if (StringUtils.isBlank(form.getFirearmsCode())) {
            throw new ServiceException("枪证号码为空");
        }
        // 默认状态是待审核
        form.setManagerStatus("0");
        policeEquipmentLogMapper.save(form);

    }

    @Override
    public void auditing(FirearmsEquipmentDTO form) {
        //得到审核人资料
        User user = userMapper.getUserMessage(form.getAccount());
        if (null == user) {
            throw new ServiceException("不存在该人员或者人员无效！！！");
        } else {
            if (!user.getPassword().equals(Encrypt.encryptByMd5(form.getPassword()))) {
                throw new ServiceException("密码不正确！！！");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date());
            form.setAuditingPerson(user.getPoliceName());
            form.setAuditingDate(time);
        }

        if (StringUtils.isBlank(form.getStatus()) ||
                (!"1".equals(form.getStatus().trim()) && !"2".equals(form.getStatus().trim()))) {
            throw new ServiceException("审核状态不合法");
        }
        //当form.getRecord()为null或者size=0时为全部审核通过
        if (null == form.getRecord() || form.getRecord().length == 0) {
            policeEquipmentLogMapper.auditingAll(form);
        } else {
            policeEquipmentLogMapper.auditing(form);
        }
    }

    @Override
    public Result findPoliceEquipmenLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<PoliceEquipmenlogVO> list = policeEquipmentLogMapper.findPoliceEquipmenLogList();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public PoliceEquipmenlogVO findPoliceEquipmenLogById(Integer id) {
        PoliceEquipmenlogVO data = policeEquipmentLogMapper.findPoliceEquipmenLogById(id);
        return data;
    }

    @Override
    public List<String> getfirearmsNoList() {
        List<String> list = policeEquipmentLogMapper.getfirearmsNoList();
        return list;
    }
}
