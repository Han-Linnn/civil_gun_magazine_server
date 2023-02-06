package com.jingde.equipment.app.firearms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.cabinets.vo.LableVO;
import com.jingde.equipment.app.cabinets.vo.RegisterListVO;
import com.jingde.equipment.app.cabinets.vo.TypeVO;
import com.jingde.equipment.app.firearms.dao.FirearmsChangeLogMapper;
import com.jingde.equipment.app.firearms.dao.FirearmsTypeMapper;
import com.jingde.equipment.app.firearms.dto.FirearmsChangeLogDTO;
import com.jingde.equipment.app.firearms.service.FirearmsChangeLogService;
import com.jingde.equipment.app.firearms.vo.TypeTitleVO;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.FirearmsChangeLog;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FirearmsChangeLogServiceImpl extends ServiceImpl<FirearmsChangeLogMapper, FirearmsChangeLog> implements FirearmsChangeLogService {

    private final FirearmsTypeMapper firearmsTypeMapper;

    private final FirearmsChangeLogMapper firearmsChangeLogMapper;

    @Override
    public RegisterListVO select() {
        RegisterListVO vo = new RegisterListVO();
        //获取变更记录
        List<TypeVO> list = firearmsChangeLogMapper.selectRegistrationChange();
        //获取弹药类型
        List<TypeTitleVO> titleList = firearmsTypeMapper.findByStatus("1");
        vo.setTitle(titleList); //类型标题
        //获取最新的一条记录
        TypeVO typeVo = firearmsChangeLogMapper.selectMaxId();

        for (TypeVO type : list) {
            List<LableVO> ammoList = new ArrayList<>();
            //统计变化数量
            List<LableVO> lableVOList = JSON.parseArray(type.getText(), LableVO.class);
            int total = 0;
            int changeTotal = 0;
            int bombTotal = 0; //炮总计
            int bombChange = 0; //炮改变数量
            int bulletTotal = 0; //枪支总数
            int bulletChange = 0; //枪支改变数量
            for (TypeTitleVO title : titleList) {
                LableVO lvo = new LableVO();
                lvo.setId(title.getId());
                lvo.setType(title.getName());
                for (LableVO l : lableVOList) {
                    if (null != lableVOList) { //统计的数量不为空
                        if (lvo.getId().equals(l.getId())) {
                            lvo.setCount(l.getCount()); //数量
                            lvo.setChangeNumber(l.getChangeNumber()); //变化数量
                            //计算总数量,总的变化数量
                            total += lvo.getCount(); //总数量
                            changeTotal += lvo.getChangeNumber(); //总改变数量
                            //计算炮的数量和变化量
                            if (title.getType().equals(1)){
                                bombTotal += l.getCount();  //炮合计数量
                                bombChange += lvo.getChangeNumber();  //炮改变数量
                            }
                            //计算枪支的数量和变化量
                            if (title.getType().equals(0)){
                                bulletTotal += l.getCount();  //枪支合计数量
                                bulletChange += lvo.getChangeNumber();  //枪支改变数量
                            }
                        }
                    }
                }
                ammoList.add(lvo);
            }
            type.setContent(ammoList);
            type.setTotal(total);
            type.setChangeTotal(changeTotal);
            type.setBombTotal(bombTotal);
            type.setBombChange(bombChange);
            type.setBulletTotal(bulletTotal);
            type.setBulletChange(bulletChange);
            type.setText(null);
            //为最新记录添加第一条记录标识
            if (typeVo.getId().equals(type.getId())) {
                type.setIsFirst(1);
            }
        }
        vo.setList(list);
        return vo;
    }

    /**
     * 根据最新的数据记录,第一次是0
     * @param form
     */
    @Override
    public void saveChangelog(FirearmsChangeLogDTO form) {
        //获取弹药类型
        List<TypeTitleVO> titleList = firearmsTypeMapper.findByStatus("1");
        //获取最新的变更记录
        TypeVO vo = firearmsChangeLogMapper.selectMaxId();
        //记录各种类型的子弹总数
        List<LableVO> firearmsListTotalList = new ArrayList<>();
        //所有的总数量
        for (TypeTitleVO title : titleList) {
            LableVO lable = new LableVO();
            lable.setType(title.getName());
            lable.setId(title.getId());
            lable.setCount(0);
            firearmsListTotalList.add(lable);
        }
        //不是第一条记录
        int countTotal = 0;
        if (null != vo) {
            List<LableVO> lableVOList = JSON.parseArray(vo.getText(), LableVO.class);
            for ( LableVO lab: lableVOList) {
                for (LableVO fir :firearmsListTotalList ) {
                    if (lab.getId().equals(fir.getId())){
                        fir.setCount(lab.getCount());
                        countTotal+=lab.getCount();
                    }
                }
            }
        }
        form.setTotal(countTotal);
        form.setContent(JSON.toJSONString(firearmsListTotalList));
        //保存新建变更记录
        firearmsChangeLogMapper.saveLog(form);
    }

    /*
    *记录枪柜里的
    *
    * @Override
    public void saveChangelog(FirearmsChangeLogDTO form) {
        //获取弹药类型
        List<TypeTitleVO> titleList = firearmsTypeMapper.findByStatus("1");
        //获取当前柜子所剩余枪支的实时数量
        List<String> strList = new ArrayList<>();
        strList.add("1");
        strList.add("4");
        strList.add("5");
        List<FirearmsTypeVO> firearmsList = cabinetSeatMapper.selectFirearmNumByCondition(strList);
        //记录各种类型的子弹总数
        List<LableVO> firearmsListTotalList = new ArrayList<>();
        //所有的总数量
        int countTotal = 0;
        for (TypeTitleVO title : titleList) {
            int typeTotal = 0;
            LableVO lable = new LableVO();
            lable.setType(title.getType());
            lable.setId(title.getId());
            for (FirearmsTypeVO ammo : firearmsList) {
                if (title.getId().equals(ammo.getFirearmsTypeId())) {
                    typeTotal += 1;
                }
            }
            lable.setCount(typeTotal);
            countTotal += typeTotal;
            typeTotal = 0;
            firearmsListTotalList.add(lable);
        }
        form.setTotal(countTotal);
        //获取最新的变更记录
        TypeVO vo = firearmsChangeLogMapper.selectMaxId();
        //统计变化数量
        if (null != vo) {
            List<LableVO> lableVOList = JSON.parseArray(vo.getText(), LableVO.class);
            for (LableVO lable : firearmsListTotalList) {
                for (LableVO l : lableVOList) {
                    if (null != lableVOList) {
                        if (lable.getId().equals(l.getId())) {
                            lable.setChangeNumber(lable.getCount() - l.getCount());
                        }
                    }
                }
            }
        }
        form.setContent(JSON.toJSONString(firearmsListTotalList));
        //保存新建变更记录
        firearmsChangeLogMapper.saveLog(form);
    }*/

    @Override
    public void updateChangelog(FirearmsChangeLogDTO form) {

        if (StringUtils.isBlank(form.getId())) {
            throw new ServiceException("id为空");
        }
        if (null == form.getAdd()) {
            form.setAdd(0);
        }
        if (null == form.getReduce()) {
            form.setReduce(0);
        }
        if (0 !=  form.getAdd()
                && 0 != form.getReduce()) {
            throw new ServiceException("增加,减少只能其中一个不为0");
        }
        form.setChangeNumber(form.getAdd() + form.getReduce());
        TypeVO vo = firearmsChangeLogMapper.selectMaxId();
        if (null != vo && vo.getId().equals(form.getId().trim())) {
            List<LableVO> lableVOList = JSON.parseArray(vo.getText(), LableVO.class);
            int total = 0;
            if (null != form.getTypeId()) {
                for (LableVO l : lableVOList) {
                    if (l.getId().equals(form.getTypeId())) {
                        l.setChangeNumber(l.getChangeNumber() + form.getChangeNumber());
                        l.setCount(l.getCount() + form.getChangeNumber()) ;
                    }
                    total += l.getCount();
                }
                form.setTotal(total);
            }
            form.setContent(JSON.toJSONString(lableVOList));
            //修改
            firearmsChangeLogMapper.updateContentbyId(form);
        } else {
            throw new ServiceException("该记录不是最新或者不存在,不能再进行修改");
        }
    }
}
