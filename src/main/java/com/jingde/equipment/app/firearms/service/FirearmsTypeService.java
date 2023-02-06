package com.jingde.equipment.app.firearms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.cabinets.dao.CabinetSeatMapper;
import com.jingde.equipment.app.duty.vo.FirearmsTypeTitleVO;
import com.jingde.equipment.app.firearms.dao.AmmoFirearmMapper;
import com.jingde.equipment.app.firearms.dao.FirearmsMapper;
import com.jingde.equipment.app.firearms.dao.FirearmsTypeMapper;
import com.jingde.equipment.app.firearms.dto.FirearmsTypeDTO;
import com.jingde.equipment.app.firearms.vo.AmmoFirearmVO;
import com.jingde.equipment.app.firearms.vo.FirearmsTypeVO;
import com.jingde.equipment.app.firearms.vo.TypeTitleVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.AmmoFirearm;
import com.jingde.equipment.model.FirearmsType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 枪支类型
 *
 * @author
 */
@Service
public class FirearmsTypeService extends ServiceImpl<FirearmsTypeMapper, FirearmsType> implements IService<FirearmsType> {

    @Resource
    private FirearmsTypeMapper firearmsTypeMapper;
    @Resource
    private AmmoFirearmService ammoFirearmService;
    @Resource
    private CabinetSeatMapper cabinetSeatMapper;
    @Resource
    private FirearmsMapper firearmsMapper;
    @Resource
    private AmmoFirearmMapper ammoFirearmMapper;

    /**
     * 添加枪支类型
     *
     * @param form
     * @param registerPerson
     * @return
     */
    public void addFirearmsType(String registerPerson, FirearmsTypeDTO form) {
        String firearmsType = form.getFirearmsType();
        String note = form.getNote();
//        String levelType = form.getLevelType();
        Integer type = form.getType();

        FirearmsType firearmsTypeModel = new FirearmsType();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String registerDate = df.format(new Date());
        firearmsTypeModel.setFirearmsType(firearmsType);
        firearmsTypeModel.setRegisterPerson(registerPerson);
        firearmsTypeModel.setRegisterDate(registerDate);
//        firearmsTypeModel.setLevelType(Integer.parseInt(levelType));//枪支等级
        firearmsTypeModel.setType(type);
        if (note != null) {
            firearmsTypeModel.setNote(note);
        }

        firearmsTypeMapper.insert(firearmsTypeModel);
    }

    /**
     * 删除枪支类型
     *
     * @param firearmId
     */
    @Transactional
    public void removeFirearmType(Integer firearmId) {
        // 删除枪支类型
        firearmsTypeMapper.deleteById(firearmId);
        // 删除适用枪支
        QueryWrapper<AmmoFirearm> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("firearm_id", firearmId);
        queryWrapper.allEq(queryMap);
        ammoFirearmService.remove(queryWrapper);
    }

    public Result firearmsTypeTitle() {
        List<FirearmsTypeTitleVO> list = firearmsTypeMapper.firearmsTypeTitle(1);
        // TODO 跟前端商量修改
        for (FirearmsTypeTitleVO fe : list) {
            fe.setValue("type" + fe.getId());
        }
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 枪支类型列表
     *
     * @return
     */
    public List<FirearmsTypeVO> firearmsTypeList() {

        return firearmsTypeMapper.firearmsTypeList();
    }

    public List<FirearmsTypeVO> availableList(String type) {
        if ("0".equals(type)) {
            return cabinetSeatMapper.availableList();
        } else {
            return cabinetSeatMapper.availableList();
        }
    }

    public FirearmsType selectById(Integer id) {
        FirearmsType firearmsType = firearmsTypeMapper.selectById(id);
        return firearmsType;
    }

    public List<FirearmsTypeVO> selectByTimeAndId(String time, Integer typeId) {
        List<FirearmsTypeVO> list = firearmsMapper.selectByTimeAndId(time, typeId);
        return list;
    }

    public List<FirearmsTypeVO> selectList(Integer type, String firearmsType) {

        List<FirearmsTypeVO> list = firearmsTypeMapper.selectListByCondition(type, firearmsType);
        if (null == null && list.size() <= 0) {
            return new ArrayList<FirearmsTypeVO>();
        }
        List<Integer> collect = list.stream().map(FirearmsTypeVO::getId).collect(Collectors.toList());
        //查询枪支适用的弹药类型
        List<AmmoFirearmVO> map = ammoFirearmMapper.selectListByIds(collect);
        for (FirearmsTypeVO firearm :list) {
            List<TypeTitleVO> ammoList = new ArrayList<>();
            for (AmmoFirearmVO ammo: map) {
                if (firearm.getId().equals(ammo.getFirearmId())) {
                    TypeTitleVO title = new TypeTitleVO();
                    title.setId(ammo.getId());
                    title.setName(ammo.getAmmoType());
                    title.setType(ammo.getType());
                    ammoList.add(title);
                }
            }
            firearm.setAmmoList(ammoList);

        }

        return list;
    }
}
