package com.jingde.equipment.app.firearms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.firearms.dao.AmmoFirearmMapper;
import com.jingde.equipment.app.firearms.dao.AmmoTypeMapper;
import com.jingde.equipment.app.firearms.dto.AmmoFirearmDTO;
import com.jingde.equipment.app.firearms.dto.AmmoTypeDTO;
import com.jingde.equipment.app.firearms.vo.AmmoFirearmVO;
import com.jingde.equipment.app.firearms.vo.AmmoTypeVO;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.AmmoFirearm;
import com.jingde.equipment.model.AmmoType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 */
@Service
public class AmmoTypeService extends ServiceImpl<AmmoTypeMapper, AmmoType> implements IService<AmmoType> {

    @Resource
    private AmmoTypeMapper ammoTypeMapper;
    @Resource
    private AmmoFirearmService ammoFirearmService;
    @Resource
    private AmmoFirearmMapper ammoFirearmMapper;

    /**
     * 弹药类型列表
     *
     * @return
     */
    public List<AmmoTypeVO> findAmmoType() {
        return ammoTypeMapper.findAmmoType();
    }

    /**
     * 添加弹药类型
     *
     * @param registerPerson
     * @param form
     */
    @Transactional
    public void addAmmoType(String registerPerson, AmmoTypeDTO form) {
        String ammoType = form.getAmmoType();
        String note = form.getNote();
        if (null == ammoType) {
            throw new ServiceException("弹药类型名称不能为空");
        }
        //获取当前登录的用户名
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String registerDate = df.format(new Date());

        AmmoType ammoTypeModel = new AmmoType();
        ammoTypeModel.setAmmoType(ammoType);
        ammoTypeModel.setRegisterPerson(registerPerson);
        ammoTypeModel.setRegisterDate(registerDate);
        if (note != null) {
            ammoTypeModel.setNote(note);
        }
        //新增弹药类型
        ammoTypeMapper.insert(ammoTypeModel);

        // 保存适用枪支
        List<AmmoFirearmDTO> ammoFirearmList = form.getAmmoFirearmList();
        // 目标列表
        ArrayList<AmmoFirearm> ammoFirearms = new ArrayList<>();
        if (ammoFirearmList != null && ammoFirearmList.size() > 0) {
            Integer ammoId = ammoTypeModel.getId();
            for (AmmoFirearmDTO ammoFirearm : ammoFirearmList) {
                Integer firearmId = ammoFirearm.getFirearmId();
                String firearmType = ammoFirearm.getFirearmType();

                AmmoFirearm ammoFirearmModel = new AmmoFirearm();
                ammoFirearmModel.setAmmoId(ammoId);
                ammoFirearmModel.setAmmoType(ammoType);
                ammoFirearmModel.setFirearmId(firearmId);
                ammoFirearmModel.setFirearmType(firearmType);

                ammoFirearms.add(ammoFirearmModel);
            }
            ammoFirearmService.saveBatch(ammoFirearms);
        }
    }

    /**
     * 更新弹药类型
     *
     * @param
     * @param form
     */
    @Transactional
    public void updateAmmoType( AmmoTypeDTO form) {

		if (null == form.getAmmoType()) {
            throw new ServiceException("弹药类型名称不能为空");
        }
        //获取当前登录的用户名
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String registerDate = df.format(new Date());
        form.setRegisterDate(registerDate);
        //新增弹药类型
        ammoTypeMapper.updateManyById(form);

        // 参数-适用枪支列表
        List<AmmoFirearmDTO> ammoFirearmList = form.getAmmoFirearmList();
        if (null == ammoFirearmList) {
            ammoFirearmList = new ArrayList<>();
        }
        ArrayList<Integer> ammoFirearmIds = new ArrayList<>();
        // 已有的适用的枪支
        List<AmmoFirearm> orignalAmmoFirearmList = ammoFirearmMapper.listByAmmoId(form.getId());

        ArrayList<Integer> orignalAmmoFirearmIds = new ArrayList<>();
        // 是否需要更新
        boolean needUpdate = true;
        if (orignalAmmoFirearmList != null || orignalAmmoFirearmList.size() > 0) {
            for (AmmoFirearm ammoFirearm : orignalAmmoFirearmList) {
                Integer orignalFirearmId = ammoFirearm.getFirearmId();
                orignalAmmoFirearmIds.add(orignalFirearmId);
            }
            for (AmmoFirearmDTO ammoFirearmDTO : ammoFirearmList) {
                Integer firearmId = ammoFirearmDTO.getFirearmId();
                ammoFirearmIds.add(firearmId);
            }
        }
        if (ammoFirearmIds.equals(orignalAmmoFirearmIds)) {
            needUpdate = false;
        }
        if (needUpdate) {
            QueryWrapper<AmmoFirearm> queryWrapper = new QueryWrapper<>();
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("ammo_id", form.getId());
            queryWrapper.allEq(queryMap);
            // 删除旧数据
            ammoFirearmMapper.delete(queryWrapper);
            // 保存适用枪支（新数据）
            // 目标列表
            ArrayList<AmmoFirearm> ammoFirearms = new ArrayList<>();
            if (ammoFirearmList != null && ammoFirearmList.size() > 0) {
                for (AmmoFirearmDTO ammoFirearm : ammoFirearmList) {
                    Integer firearmId = ammoFirearm.getFirearmId();
                    String firearmType = ammoFirearm.getFirearmType();
                    AmmoFirearm ammoFirearmModel = new AmmoFirearm();
                    ammoFirearmModel.setAmmoId(form.getId());
                    ammoFirearmModel.setAmmoType(form.getAmmoType());
                    ammoFirearmModel.setFirearmId(firearmId);
                    ammoFirearmModel.setFirearmType(firearmType);
                    ammoFirearms.add(ammoFirearmModel);
                }
                ammoFirearmService.saveBatch(ammoFirearms);
            }
        }
    }

    /**
     * 删除 弹药类型
     *
     * @param id
     */
    @Transactional
    public void removeAmmoType(Integer id) {
        // 删除弹药类型基本信息
        ammoTypeMapper.deleteById(id);
        // 删除弹药适用的枪支
        QueryWrapper<AmmoFirearm> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("ammo_id", id);
        queryWrapper.allEq(queryMap);
        ammoFirearmService.remove(queryWrapper);
    }

    public List<AmmoFirearmVO> select(Integer type, Integer firearmsTypeId, String ammoType) {
        List<AmmoFirearmVO> list = ammoFirearmMapper.selectList(type,firearmsTypeId,ammoType);
        return list;
    }
}
