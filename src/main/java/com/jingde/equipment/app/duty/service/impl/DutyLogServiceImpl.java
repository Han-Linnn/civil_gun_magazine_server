package com.jingde.equipment.app.duty.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.duty.dao.DutyLogMapper;
import com.jingde.equipment.app.duty.dto.DutyLogDTO;
import com.jingde.equipment.app.duty.service.DutyLogService;
import com.jingde.equipment.app.duty.vo.DataVO;
import com.jingde.equipment.app.duty.vo.DutyLogVO;
import com.jingde.equipment.app.duty.vo.FirearmsDataVO;
import com.jingde.equipment.app.firearms.dao.FirearmsMapper;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.DutyLog;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DutyLogServiceImpl extends ServiceImpl<DutyLogMapper, DutyLog> implements DutyLogService {

    private final DutyLogMapper dutyLogMapper;

    private final FirearmsMapper firearmsMapper;

    @Override
    public void updateNewest() {
    }

    @Override
    public Result onDutyLogList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {

        //分页查询
        PageHelper.startPage(page, size);
        List<DutyLogVO> list = dutyLogMapper.onDutyLogList();
        PageInfo<DutyLogVO> pageInfo = new PageInfo(list);
        //枪支/弹药数量详情
        for (DutyLogVO dg : pageInfo.getList()) {
            if (!StringUtils.isBlank(dg.getContent())) {
                //解析出错数据跳过
                try {
                    List<FirearmsDataVO> firearmsDatumVOS = JSON.parseArray(dg.getContent(), FirearmsDataVO.class);
                    // TODO 跟前端商量修改
                    for (FirearmsDataVO vo : firearmsDatumVOS) {
                        vo.setValue("type" + vo.getKey());
                    }
                    dg.setDataList(firearmsDatumVOS);
                } catch (Exception e) {

                } finally {
                    dg.setContent(null);
                }
            }
            // dg.setContent(null);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Override
    public Result lastDutyLog() {
        //得到最后一条加进去的数据
        DutyLogVO data = dutyLogMapper.lastDutyLog();
        //枪支/弹药数量详情
        if (null != data) {
            if (!StringUtils.isBlank(data.getContent())) {
                //解析出错数据跳过
                try {
                    List<FirearmsDataVO> firearmsDatumVOS = JSON.parseArray(data.getContent(), FirearmsDataVO.class);
                    data.setDataList(firearmsDatumVOS);
                } catch (Exception e) {

                } finally {
                    data.setContent(null);
                }
            }
            return ResultGenerator.genSuccessResult(data);
        } else {
            DutyLogVO dutyLogVo = new DutyLogVO();
            dutyLogVo.setDataList(new ArrayList<FirearmsDataVO>());
            return ResultGenerator.genSuccessResult(dutyLogVo);
        }
    }

    @Override
    public void add(DutyLogDTO vo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        DutyLog dutyLog = new DutyLog();
        if (null != vo.getDate()) {
            dutyLog.setDutyDate(vo.getDate());
        } else {
            dutyLog.setDutyDate(time);
        }
        dutyLog.setNote(vo.getNotes());

        //获取上一条值班交接登记的枪支弹药统计情况
        DutyLogVO dutyLogVO = dutyLogMapper.lastDutyLog();

        if (null != dutyLogVO && null != dutyLogVO.getContent()) {

            //将枪支弹药数量统计的json转成对象
            List<FirearmsDataVO> firearmsDataVOS = JSON.parseArray(dutyLogVO.getContent(), FirearmsDataVO.class);
            //统计过的枪炮类型id
            List<Integer> collect = firearmsDataVOS.stream().map(FirearmsDataVO::getKey).collect(Collectors.toList());
            //获取当前系统有的枪炮类型跟数量
            List<DataVO> dataVOS = firearmsMapper.selectTotal();
            for (DataVO data : dataVOS) {
                //不存在
                if (!collect.contains(data.getKey())) {
                    FirearmsDataVO firearmsDataVO = new FirearmsDataVO();
                    firearmsDataVO.setKey(data.getKey());
                    firearmsDataVO.setValue(data.getValue());
                    firearmsDataVO.setType(data.getType());
                    if (null == data.getTmpBullet()) {
                        data.setTmpBullet(0);
                    }
                    if (null == data.getTmpGun()) {
                        data.setTmpGun(0);
                    }
                    firearmsDataVO.setBullet(new String[]{data.getTmpBullet() + ""});
                    firearmsDataVO.setGun(new String[]{data.getTmpGun() + ""});
                    firearmsDataVOS.add(firearmsDataVO);
                }

            }
            if (null != firearmsDataVOS && 0 < firearmsDataVOS.size()) {
                for (int j = 0; j < firearmsDataVOS.size(); j++) {
                    FirearmsDataVO firearmsDataVO = firearmsDataVOS.get(j);
                    //计算出原来的数量
                    String[] gun = firearmsDataVO.getGun();
                    Integer originalFirearmsTypeCount;
                    if (gun.length > 1) {
                        originalFirearmsTypeCount = Integer.parseInt(gun[0]) + Integer.parseInt(gun[1]);
                    } else {
                        originalFirearmsTypeCount = Integer.parseInt(gun[0]);
                    }
                    //枪支统计数量
                    String[] newGun = new String[]{originalFirearmsTypeCount + ""};
                    //计算原来的弹药数量
                    String[] bullet = firearmsDataVO.getBullet();
                    Integer originalAmmoTypeCount;
                    if (bullet.length > 1) {
                        originalAmmoTypeCount = Integer.parseInt(bullet[0]) + Integer.parseInt(bullet[1]);
                    } else {
                        originalAmmoTypeCount = Integer.parseInt(bullet[0]);
                    }
                    //枪支统计数量
                    String[] newBullet = new String[]{originalAmmoTypeCount + ""};
                    firearmsDataVO.setGun(newGun);
                    firearmsDataVO.setBullet(newBullet);
                    for (int i = 0; i < collect.size(); i++) {

                    }
                }
                dutyLogVO.setDutyContent(JSON.toJSONString(firearmsDataVOS));
            }
            dutyLog.setDutyContent(dutyLogVO.getDutyContent());
        } else {
            //未有值班记录
            DutyLogVO dutyLogVO1 = new DutyLogVO();
            List<FirearmsDataVO> firearmsDataVOS = new ArrayList<>();
            List<DataVO> list = firearmsMapper.selectTotal();
            for (DataVO data : list) {
                FirearmsDataVO dataVO = new FirearmsDataVO();
                dataVO.setKey(data.getKey());
                dataVO.setType(data.getType());
                dataVO.setValue(data.getValue());
                if (null == data.getTmpBullet()) {
                    data.setTmpBullet(0);
                }
                if (null == data.getTmpGun()) {
                    data.setTmpGun(0);
                }
                dataVO.setBullet(new String[]{data.getTmpBullet() + ""});
                dataVO.setGun(new String[]{data.getTmpGun() + ""});
                firearmsDataVOS.add(dataVO);
            }
            dutyLogVO1.setDutyContent(JSON.toJSONString(firearmsDataVOS));
            dutyLog.setDutyContent(dutyLogVO1.getDutyContent());
        }
        if (null == dutyLogVO) {
            dutyLogMapper.insert(dutyLog);
        } else {
            if (null != dutyLogVO.getContent() && null != dutyLogVO.getOffDutyPerson()
                    && null != dutyLogVO.getOffDutyPerson2() && null != dutyLogVO.getOnDutyPerson()
                    && null != dutyLogVO.getOnDutyPerson2()) {
                dutyLogMapper.insert(dutyLog);
            } else {
                throw new ServiceException("有未确认的签名，不允许添加新的值班记录！");
            }
        }
    }

    @Override
    public void update(DutyLogDTO vo) {
        //判断是最新的,且没有签名完成的方可以修改
        DutyLogVO dutyLogVO = dutyLogMapper.selectMaxId();
        if (!vo.getId().equals(dutyLogVO.getId())) {
            throw new ServiceException("不是最新记录不能进行修改");
        }
        if (vo.getId().equals(dutyLogVO.getId()) && null != dutyLogVO.getOnDutyPerson() && null != dutyLogVO.getOnDutyPerson2()) {
            throw new ServiceException("该值班记录已经完成,需要新添值班记录");
        }
        DutyLog dutyLog = new DutyLog();
        dutyLog.setNote(vo.getNotes());
        dutyLog.setDutyDate(vo.getDate());
        //JSON ==> String
        String data = JSON.toJSONString(vo.getDataList());
        dutyLog.setDutyContent(data);
        dutyLog.setUpdateTime(new Date());
        dutyLog.setId(Integer.valueOf(vo.getId()));
        dutyLogMapper.updateById(dutyLog);
    }
}
