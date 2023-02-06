package com.jingde.equipment.app.apply.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.apply.dao.*;
import com.jingde.equipment.app.apply.dto.*;
import com.jingde.equipment.app.apply.service.FirearmsApplyLogService;
import com.jingde.equipment.app.apply.vo.*;
import com.jingde.equipment.app.firearms.dao.AmmoTypeMapper;
import com.jingde.equipment.app.firearms.dao.FirearmsMapper;
import com.jingde.equipment.app.firearms.dao.FirearmsTypeMapper;
import com.jingde.equipment.app.firearms.dto.AmmoTypeDTO;
import com.jingde.equipment.app.firearms.vo.FirearmsTypeVO;
import com.jingde.equipment.app.record.dao.ApprovalLogMapper;
import com.jingde.equipment.app.system.dao.PermissionMapper;
import com.jingde.equipment.app.user.service.UserService;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.*;
import com.jingde.equipment.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.jingde.equipment.constant.ServiceConstant.*;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FirearmsApplyLogServiceImpl extends ServiceImpl<FirearmsApplyLogMapper, FirearmsApplyLog> implements FirearmsApplyLogService {


    private final UserService userService;
    private final FirearmsApplyLogMapper firearmsApplyLogMapper;
    private final FirearmsApplyTypeLogMapper firearmsApplyTypeLogMapper;
    private final FirearmsTypeMapper firearmsTypeMapper;
    private final FirearmsMapper firearmsMapper;
    private final AmmoTypeMapper ammoTypeMapper;
    private final PermissionMapper permissionMapper;
    private final FirearmsApplyTypeInfoMapper firearmsApplyTypeInfoMapper;
    private final FirearmsReceiveBatchMapper firearmsReceiveBatchMapper;
    private final FirearmsReturnBatchMapper firearmsReturnBatchMapper;
    private final AmmoReceiveBatchInfoMapper ammoReceiveBatchInfoMapper;
    private final AmmoReturnBatchInfoMapper ammoReturnBatchInfoMapper;
    private final ApprovalLogMapper approvalLogMapper;

    @Override
    public void add(FirearmsApplyLogForm form) {
        if (StringUtils.isBlank(form.getApplyDate())) {
            throw new ServiceException("申请领取时间不能为空");
        }
        if (StringUtils.isBlank(form.getReturnDate())) {
            throw new ServiceException("归还时间不能为空");
        }
        //保存记录,并返回记录id
        firearmsApplyLogMapper.insertByForm(form);
        if (null == form.getId()) {
            Integer id = firearmsApplyLogMapper.selectMaxId();
            form.setId(id);
        }
        //申请的枪炮类型集合
        List<FirearmsApplyTypeLogDTO> list = form.getList();
        if (null == list || 0 >= list.size()) {
            throw new ServiceException("请选择要申请的枪炮类型");
        }
        List<FirearmsTypeVO> firearmsTypeVOS = firearmsMapper.selectByTimeAndId(form.getApplyDate(), null);
        Iterator<FirearmsApplyTypeLogDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            FirearmsApplyTypeLogDTO typeDto = iterator.next();
            typeDto.setApplyId(form.getId());
            if (null == typeDto.getApplyFirearmCount() || 0 >= typeDto.getApplyFirearmCount()) {
                iterator.remove();
            }
            if (null == typeDto.getApplyFirearmCount() || 0 == typeDto.getApplyFirearmCount()) {
                throw new ServiceException("枪炮数量不能为空");
            }
            if (null == typeDto.getApplyAmmoCount()) {
                typeDto.setApplyAmmoCount(0);
            }
            if (null == typeDto.getFirearmTypeId()) {
                throw new ServiceException("枪炮类型id为空");
            }
            //申请的枪炮数量不能多于在库的
            for (FirearmsTypeVO typeVo : firearmsTypeVOS) {
                if (typeDto.getFirearmTypeId().equals(typeVo.getFirearmsTypeId())
                        && typeDto.getApplyFirearmCount() > typeVo.getCanApply()) {
                    throw new ServiceException("轻炮类型的申请数量超过可申请的数量");
                }
            }
            FirearmsType firearmsType = firearmsTypeMapper.selectById(typeDto.getFirearmTypeId());
            if (null == firearmsType) {
                throw new ServiceException("枪炮类型不存在,请重新选择");
            }
            typeDto.setFirearmType(firearmsType.getFirearmsType());
            //可以不领取子弹
            if (null != typeDto.getAmmoTypeId()) {
                AmmoType ammoType = ammoTypeMapper.selectById(typeDto.getAmmoTypeId());
                if (null == ammoType) {
                    throw new ServiceException("弹药类型不存在,请重新选择");
                }
                if (null != typeDto.getApplyAmmoCount() && ammoType.getNumber() < typeDto.getApplyAmmoCount()) {
                    throw new ServiceException("[" + ammoType.getAmmoType() + "]类型的弹药超库存数量");
                }
                typeDto.setAmmoType(ammoType.getAmmoType());
            }
        }
        firearmsApplyTypeLogMapper.insertByList(list);
    }

    @Override
    public void updateById(User user, FirearmsApplyLogForm form) {
        //只有状态01,02,08才可修改,修改后状态为01,待审核
        FirearmsApplyLog firearmsApplyLog = firearmsApplyLogMapper.selectById(form.getId());
        if (!firearmsApplyLog.getApplyPersonId().equals(user.getId())) {
            throw new ServiceException("修改错误(不能修改别人的申请单)");
        }
        if (!APPLY_STATUS_EXAMINE.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_RECEIVE.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_CANCEL.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("当前状态,申请单不能修改");
        }
        if (StringUtils.isBlank(form.getApplyDate())) {
            throw new ServiceException("申请领取时间不能为空");
        }
        if (StringUtils.isBlank(form.getReturnDate())) {
            throw new ServiceException("归还时间不能为空");
        }
        form.setStatus(APPLY_STATUS_EXAMINE);
        form.setAuditingPerson(null);
        form.setAuditingPersonId(null);
        form.setNote(null);
        //更新申请记录
        firearmsApplyLogMapper.updateFormById(form);
        //删除原先的申请类型记录
        firearmsApplyTypeLogMapper.deleteByLogId(form.getId());
        //申请的枪炮类型集合
        List<FirearmsApplyTypeLogDTO> list = form.getList();
        if (null == list || 0 >= list.size()) {
            throw new ServiceException("请选择要申请的枪炮类型");
        }
        List<FirearmsTypeVO> firearmsTypeVOS = firearmsMapper.selectByTimeAndId(form.getApplyDate(), null);
        Iterator<FirearmsApplyTypeLogDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            FirearmsApplyTypeLogDTO typeDto = iterator.next();
            typeDto.setApplyId(form.getId());
            if (null == typeDto.getApplyFirearmCount() || 0 >= typeDto.getApplyFirearmCount()) {
                iterator.remove();
            }
            if (null == typeDto.getApplyFirearmCount() || 0 == typeDto.getApplyFirearmCount()) {
                throw new ServiceException("枪炮数量不能为空");
            }
            if (null == typeDto.getFirearmTypeId()) {
                throw new ServiceException("枪炮类型id为空");
            }
            //申请的枪炮数量不能多于在库的
            for (FirearmsTypeVO typeVo : firearmsTypeVOS) {
                if (typeDto.getFirearmTypeId().equals(typeVo.getFirearmsTypeId())
                        && typeDto.getApplyFirearmCount() > typeVo.getCanApply()) {
                    throw new ServiceException("轻炮类型的申请数量超过可申请的数量");
                }
            }
            if (null == typeDto.getApplyAmmoCount()) {
                typeDto.setApplyAmmoCount(0);
            }
            FirearmsType firearmsType = firearmsTypeMapper.selectById(typeDto.getFirearmTypeId());
            if (null == firearmsType) {
                throw new ServiceException("枪炮类型不存在,请重新选择");
            }
            typeDto.setFirearmType(firearmsType.getFirearmsType());
            //可以不领取子弹
            if (null != typeDto.getAmmoTypeId()) {
                AmmoType ammoType = ammoTypeMapper.selectById(typeDto.getAmmoTypeId());
                if (null == ammoType) {
                    throw new ServiceException("弹药类型不存在,请重新选择");
                }
                if (null != typeDto.getApplyAmmoCount() && ammoType.getNumber() < typeDto.getApplyAmmoCount()) {
                    throw new ServiceException("[" + ammoType.getAmmoType() + "]类型的弹药超库存数量");
                }
                typeDto.setAmmoType(ammoType.getAmmoType());
            }
        }
        firearmsApplyTypeLogMapper.insertByList(list);
    }

    @Override
    public void updateApprovalById(HttpServletRequest request,User user, FirearmsApplyLogForm form) {
        //验证密码
        Integer auditingPersonId = form.getAuditingPersonId();
        String password = form.getAuditingPassword();
        Map<String, String> map = new HashMap<>();
        map.put(String.valueOf(auditingPersonId), password);
        List<String> auditingPersonList = userService.verifyUserByAccount(map, null);
        String[] split = auditingPersonList.get(0).split("-");
        form.setAuditingPersonId(Integer.parseInt(split[0]));
        form.setAuditingPerson(split[1]);
        //判断是否有审批权限
        int itExist = permissionMapper.selectItExistByUserIdAndPer(auditingPersonId, "applyLog:examine");
        if (itExist <= 0) {
            throw new ServiceException("暂无审批权限");
        }
        if (!APPLY_STATUS_RECEIVE.equals(form.getStatus()) && !APPLY_STATUS_NO_EXAMINE.equals(form.getStatus())) {
            throw new ServiceException("不支持此状态操作");
        }
        FirearmsApplyLog firearmsApplyLog = firearmsApplyLogMapper.selectById(form.getId());
        if (!APPLY_STATUS_EXAMINE.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("当前状态不能审批申请单");
        }
        //审批
        firearmsApplyLogMapper.updateApprovalById(form);

        //保存审批日志
        ApprovalLog log = new ApprovalLog();
        log.setFirearmsUsedLogId(Integer.toString(form.getId()));
        log.setApprovalPersonId(Integer.toString(form.getAuditingPersonId()));
        log.setApprovalDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        log.setLoginAccountId(Integer.toString(user.getId()));
        log.setLoginIp(HttpUtil.getIpAddress(request));
        approvalLogMapper.save(log);

    }


    @Override
    public void updateStatusById(Integer id, String status) {
        FirearmsApplyLog firearmsApplyLog = firearmsApplyLogMapper.selectById(id);
        if (APPLY_STATUS_CANCEL.equals(status)
                && !APPLY_STATUS_EXAMINE.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_RECEIVE.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("当前状态,申请单无法取消");
        }
        if (APPLY_STATUS_RECEIVE_NOT_RETURN.equals(status)
                && !APPLY_STATUS_RECEIVE_ING.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("当前状态,申请单无法部分变为部分领取完成");
        }
        firearmsApplyLogMapper.updateStatusById(id, status);

    }


    @Override
    public PageInfo selectPageByCondition(FirearmsApplyLogDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<FirearmsApplyLogVo> list = firearmsApplyLogMapper.selectPageByCondition(dto);
        if (null == list || 0 >= list.size()) {
            return new PageInfo<>(new ArrayList<>());
        }
        //获取所有的集合
        List<Integer> ids = list.stream().map(FirearmsApplyLogVo::getId).collect(Collectors.toList());
        //查询出枪操作人集合
        List<FirearmsReceiveBatchVo> receiveBatchVos = firearmsReceiveBatchMapper.selectByApplyIds(ids);
        //查询还枪操作人集合
        List<FirearmsReturnBatchVo> returnBatchVos = firearmsReturnBatchMapper.selectByApplyIds(ids);
        //获取枪炮弹药领取情况
        List<FirearmsApplyTypeLogVo> firearmsApplyTypeLogVos = firearmsApplyTypeLogMapper.selectByApplyIds(ids);

        for (FirearmsApplyLogVo logVo : list) {
            //借枪批次
            List<FirearmsReceiveBatchVo> resltReceiveBatchVos = new ArrayList<>();
            //还枪批次
            List<FirearmsReturnBatchVo> resltReturnBatchVos = new ArrayList<>();
            //申请的枪支类型情况
            List<FirearmsApplyTypeLogVo> resultTypeLogVos = new ArrayList<>();
            for (FirearmsReceiveBatchVo batchVo : receiveBatchVos) {
                if (batchVo.getApplyId().equals(logVo.getId())) {
                    resltReceiveBatchVos.add(batchVo);
                }
            }
            for (FirearmsReturnBatchVo batchVo : returnBatchVos) {
                if (logVo.getId().equals(batchVo.getApplyId())) {
                    resltReturnBatchVos.add(batchVo);
                }
            }
            for (FirearmsApplyTypeLogVo typeVo : firearmsApplyTypeLogVos) {
                if (typeVo.getApplyId().equals(logVo.getId())) {
                    resultTypeLogVos.add(typeVo);
                }
            }
            logVo.setReceiveBatchs(resltReceiveBatchVos);
            logVo.setReturnBatchs(resltReturnBatchVos);
            logVo.setTypes(resultTypeLogVos);
        }
        return new PageInfo<>(list);
    }

    @Override
    public FirearmsApplyLogVo findInfoById(Integer id) {
        //领取情况,领取的枪支,弹药情况
        FirearmsApplyLogVo firearmsApplyLog = firearmsApplyLogMapper.findInfoById(id);
        if (null == firearmsApplyLog) {
            return new FirearmsApplyLogVo();
        }
        //查询出枪操作人集合
        List<FirearmsReceiveBatchVo> receiveBatchVos = firearmsReceiveBatchMapper.selectByApplyId(id);
        //查询还枪操作人集合
        List<FirearmsReturnBatchVo> returnBatchVos = firearmsReturnBatchMapper.selectByApplyId(id);
        //查询申请的类型
        List<FirearmsApplyTypeLogVo> types = firearmsApplyTypeLogMapper.selectByApplyId(id);
        //查询具体的枪支
        List<FirearmsApplyTypeInfoVo> infos = firearmsApplyTypeInfoMapper.selectByApplyId(id);
        for (FirearmsApplyTypeLogVo type : types) {
            List<FirearmsApplyTypeInfoVo> list = new ArrayList<>();
            for (FirearmsApplyTypeInfoVo info : infos) {
                if (type.getId().equals(info.getApplyTypeId())) {
                    list.add(info);
                }
            }
            type.setInfos(list);
        }
        firearmsApplyLog.setTypes(types);
        firearmsApplyLog.setReceiveBatchs(receiveBatchVos);
        firearmsApplyLog.setReturnBatchs(returnBatchVos);
        return firearmsApplyLog;
    }

    @Override
    public void updateScanOutById(FirearmsReceiveBatchDTO dto) {
        FirearmsApplyLog firearmsApplyLog = firearmsApplyLogMapper.selectById(dto.getApplyId());
        if (null == firearmsApplyLog) {
            throw new ServiceException("申请单不存在");
        }
        if (!APPLY_STATUS_RECEIVE.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_RECEIVE_ING.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("申请单暂时不能领取");
        }
        //验证保管人,领取人
        //验证领枪人
        Integer confirmPersonId = dto.getConfirmPersonId();
        String password = dto.getConfirmPersonPassword();
        Map<String, String> map = new HashMap<>();
        map.put(String.valueOf(confirmPersonId), password);
        List<String> auditingPersonList = userService.verifyUserByAccount(map, null);
        String[] confirmPerson = auditingPersonList.get(0).split("-");
        dto.setConfirmPersonId(Integer.parseInt(confirmPerson[0]));
        dto.setConfirmPerson(confirmPerson[1]);
        //验证保管人
        List<String> list = userService.selectUserByCode(dto.getLableList());
        String[] split = list.get(0).split("-");
        dto.setKeepingPersonId(Integer.parseInt(split[0]));
        dto.setKeepingPerson(split[1]);
        String[] split1 = list.get(1).split("-");
        dto.setKeepingPerson2Id(Integer.parseInt(split1[0]));
        dto.setKeepingPerson2(split1[1]);
        //判断是否有审批权限
        int itExist = permissionMapper.selectItExistByUserIdAndPer(dto.getKeepingPersonId(), "firearms:scanOut");
        if (itExist <= 0) {
            throw new ServiceException("[" + dto.getKeepingPerson() + "]" + "暂无该权限");
        }
        int itExist2 = permissionMapper.selectItExistByUserIdAndPer(dto.getKeepingPerson2Id(), "firearms:scanOut");
        if (itExist2 <= 0) {
            throw new ServiceException("[" + dto.getKeepingPerson2() + "]" + "暂无该权限");
        }
        //保存领取记录
        firearmsReceiveBatchMapper.insertByDto(dto);
        Integer id = dto.getId();
        if (null == id) {
            id = firearmsReceiveBatchMapper.selectMaxId();
        }
        //弹药领取情况
        List<FirearmsApplyTypeLogDTO> types = dto.getTypes();
        //具体枪号领取集合
        List<FirearmsApplyTypeInfoDTO> typeInfos = dto.getInfos();
        //获取已经领取的情况
        List<FirearmsApplyTypeLogVo> firearmsApplyTypeLogVos = firearmsApplyTypeLogMapper.selectByApplyId(dto.getApplyId());
        boolean flag = true;
        //更新弹药数量
        List<AmmoTypeDTO> ammoList = new ArrayList<>();
        for (FirearmsApplyTypeLogDTO type : types) {
            for (FirearmsApplyTypeLogVo logVo : firearmsApplyTypeLogVos) {
                if (type.getId().equals(logVo.getId())) {
                    type.setAmmoTypeId(logVo.getAmmoTypeId());
                    type.setAmmoType(logVo.getAmmoType());
                    type.setFirearmTypeId(logVo.getFirearmTypeId());
                    type.setFirearmType(logVo.getFirearmType());
                }
            }
            int count = 0;
            for (FirearmsApplyTypeInfoDTO info : typeInfos) {
                if (type.getId().equals(info.getApplyTypeId())) {
                    count += 1;
                    info.setFirearmTypeId(type.getFirearmTypeId());
                    info.setFirearmType(type.getFirearmType());
                }
                info.setReceiveBatchId(id);
                info.setApplyId(dto.getApplyId());
            }
            type.setApplyId(dto.getApplyId());
            type.setReceiveBatchId(id);
            type.setReceivedFirearmCount(count);
            if (null == type.getReceivedAmmoCount()) {
                type.setReceivedAmmoCount(0);
            } else {
                AmmoTypeDTO ammo = new AmmoTypeDTO();
                ammo.setId(type.getAmmoTypeId());
                ammo.setNumber(type.getReceivedAmmoCount());
                ammoList.add(ammo);
            }
            for (FirearmsApplyTypeLogVo logVo : firearmsApplyTypeLogVos) {
                if (logVo.getId().equals(type.getId())) {
                    type.setAmmoTypeId(logVo.getAmmoTypeId());
                    type.setAmmoType(logVo.getAmmoType());
                    type.setFirearmType(logVo.getFirearmType());
                    if (logVo.getApplyFirearmCount() < type.getReceivedFirearmCount() + logVo.getReceivedFirearmCount()) {
                        throw new ServiceException("类型为[" + logVo.getFirearmType() + "]的数量已经超过申请数量");
                    }
                    if (logVo.getApplyAmmoCount() < type.getReceivedAmmoCount() + logVo.getReceivedAmmoCount()) {
                        throw new ServiceException("类型为[" + logVo.getFirearmType() + "]的弹药数量已经超过申请数量");
                    }
                    //判断数量足够,将改变状态为领取完成
                    if (logVo.getApplyFirearmCount() > type.getReceivedFirearmCount() + logVo.getReceivedFirearmCount()) {
                        flag = false;
                    }
                    if (logVo.getApplyAmmoCount() > type.getReceivedAmmoCount() + logVo.getReceivedAmmoCount()) {
                        flag = false;
                    }
                }
            }
        }
        //保存领取详情
        firearmsApplyTypeInfoMapper.insertByList(typeInfos);
        List<String> firearmsIds = typeInfos.stream().map(FirearmsApplyTypeInfoDTO::getFirearmNo).collect(Collectors.toList());
        //更新枪支状态为不在库
        firearmsMapper.updateFirearmsStatus(firearmsIds, 1, firearmsApplyLog.getReturnDate());
        //更新领取情况
        firearmsApplyTypeLogMapper.updateReceivedCountByIds(types);
        //删除为零的弹药领取
        types.removeIf(next -> next.getReceivedAmmoCount() == 0);
        if (types.size() > 0) {
            //保存弹药领取批次
            ammoReceiveBatchInfoMapper.insertByList(types);
        }
        if (ammoList.size() > 0) {
            //更新弹药数量(减少)
            ammoTypeMapper.updateReduceCountByList(ammoList);
        }
        // 如果flag = true
        if (flag) {
            firearmsApplyLogMapper.updateStatusById(dto.getApplyId(), APPLY_STATUS_ALL_RECEIVE_NOT_RETURN);
        } else {
            firearmsApplyLogMapper.updateStatusById(dto.getApplyId(), APPLY_STATUS_RECEIVE_ING);

        }
    }

    @Override
    public void updateScanComeById(FirearmsReturnBatchDTO dto) {
        FirearmsApplyLog firearmsApplyLog = firearmsApplyLogMapper.selectById(dto.getApplyId());
        if (null == firearmsApplyLog) {
            throw new ServiceException("申请单不存在");
        }
        if (!APPLY_STATUS_ALL_RECEIVE_NOT_RETURN.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_RECEIVE_NOT_RETURN.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_PART_RETURN.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("申请单暂时不能归还");
        }
        //验证保管人,还枪人
        //验证还枪人
        Integer auditingPersonId = dto.getConfirmPersonId();
        String password = dto.getConfirmPersonPassword();
        Map<String, String> map = new HashMap<>();
        map.put(String.valueOf(auditingPersonId), password);
        List<String> auditingPersonList = userService.verifyUserByAccount(map, null);
        String[] confirmPerson = auditingPersonList.get(0).split("-");
        dto.setConfirmPersonId(Integer.parseInt(confirmPerson[0]));
        dto.setConfirmPerson(confirmPerson[1]);
        //验证保管人
        List<String> list = userService.selectUserByCode(dto.getLableList());
        String[] split = list.get(0).split("-");
        dto.setKeepingPersonId(Integer.parseInt(split[0]));
        dto.setKeepingPerson(split[1]);
        String[] split1 = list.get(1).split("-");
        dto.setKeepingPerson2Id(Integer.parseInt(split1[0]));
        dto.setKeepingPerson2(split1[1]);
        //判断是否有审批权限
        int itExist = permissionMapper.selectItExistByUserIdAndPer(dto.getKeepingPersonId(), "firearms:scanCome");
        if (itExist <= 0) {
            throw new ServiceException("[" + dto.getKeepingPerson() + "]" + "暂无该权限");
        }
        int itExist2 = permissionMapper.selectItExistByUserIdAndPer(dto.getKeepingPerson2Id(), "firearms:scanCome");
        if (itExist2 <= 0) {
            throw new ServiceException("[" + dto.getKeepingPerson2() + "]" + "暂无该权限");
        }
        //保存归还记录
        firearmsReturnBatchMapper.insertByDto(dto);
        Integer id = dto.getId();
        if (null == id) {
            id = firearmsReturnBatchMapper.selectMaxId();
        }
        //具体枪号领取集合
        List<FirearmsApplyTypeLogDTO> types = dto.getTypes();
        //统计各个类型领取情况
        List<FirearmsApplyTypeInfoDTO> typeInfos = dto.getInfos();
        //获取已经领取的情况
        List<FirearmsApplyTypeLogVo> firearmsApplyTypeLogVos = firearmsApplyTypeLogMapper.selectByApplyId(dto.getApplyId());

        //判断归还数量是否已经全部归还,全部归还修改申领状态
        boolean flag = true;
        //更新弹药数量
        List<AmmoTypeDTO> ammoList = new ArrayList<>();
        for (FirearmsApplyTypeLogDTO type : types) {
            for (FirearmsApplyTypeLogVo logVo : firearmsApplyTypeLogVos) {
                if (type.getId().equals(logVo.getId())) {
                    type.setAmmoTypeId(logVo.getAmmoTypeId());
                    type.setAmmoType(logVo.getAmmoType());
                    type.setFirearmTypeId(logVo.getFirearmTypeId());
                    type.setFirearmType(logVo.getFirearmType());
                }
            }
            int count = 0;
            for (FirearmsApplyTypeInfoDTO info : typeInfos) {
                if (type.getId().equals(info.getApplyTypeId())) {
                    count += 1;
                    info.setFirearmTypeId(type.getFirearmTypeId());
                    info.setFirearmType(type.getFirearmType());
                    //申领类型id
                    info.setApplyTypeId(type.getId());
                }
                //枪炮的归还批次id
                info.setReturnBatchId(id);
                //申领记录id
                info.setApplyId(dto.getApplyId());
            }
            type.setApplyId(dto.getApplyId());
            type.setReturnBatchId(id);
            type.setReturnedFirearmCount(count);
            if (null == type.getReturnedAmmoCount()) {
                type.setReturnedAmmoCount(0);
            } else {
                AmmoTypeDTO ammo = new AmmoTypeDTO();
                ammo.setId(type.getAmmoTypeId());
                ammo.setNumber(type.getReturnedAmmoCount());
                ammoList.add(ammo);
            }
            for (FirearmsApplyTypeLogVo logVo : firearmsApplyTypeLogVos) {
                if (logVo.getId().equals(type.getId())) {
                    type.setAmmoType(logVo.getAmmoType());
                    type.setFirearmType(logVo.getFirearmType());
                    if (logVo.getReceivedFirearmCount() < type.getReturnedFirearmCount() + logVo.getReturnedFirearmCount()) {
                        throw new ServiceException("类型为[" + logVo.getFirearmType() + "]的数量已经超过领取的数量");
                    }
                    if (logVo.getReceivedAmmoCount() < type.getReturnedAmmoCount() + logVo.getReturnedAmmoCount()) {
                        throw new ServiceException("类型为[" + logVo.getFirearmType() + "]的弹药数量已经超过领取的数量");
                    }
                }
                //判断数量足够,将改变状态为领取完成
                if (logVo.getReceivedFirearmCount() > type.getReturnedFirearmCount() + logVo.getReturnedFirearmCount()) {
                    flag = false;
                }
            }
        }
        //更新枪支领取状态为(已归还)
        firearmsApplyTypeInfoMapper.updateListByIds(typeInfos);

        //更新枪支状态为在库
        List<String> firearmsIds = typeInfos.stream().map(FirearmsApplyTypeInfoDTO::getFirearmNo).collect(Collectors.toList());
        firearmsMapper.updateFirearmsStatus(firearmsIds, 0, null);
        //更新归还情况
        firearmsApplyTypeLogMapper.updateReturnCountByIds(types);
        //删除为零的弹药归还
        types.removeIf(next -> next.getReturnedAmmoCount() == 0);
        if (types.size() > 0) {
            //保存弹药归还批次
            ammoReturnBatchInfoMapper.insertByList(types);
        }
        if (ammoList.size() > 0) {
            //更新弹药数量(增加)
            ammoTypeMapper.updateIncreaseCountByList(ammoList);
        }
        //判断归还枪支数量,如果已全部归还,申领单变为已完成
        if (flag) { //全部归还,申领状态改为已完成
            firearmsApplyLogMapper.updateStatusById(dto.getApplyId(), APPLY_STATUS_COMPLETE);
        } else { //部分归还,申领状态改为部分归还
            firearmsApplyLogMapper.updateStatusById(dto.getApplyId(), APPLY_STATUS_PART_RETURN);
        }

    }

    @Override
    public List<FirearmsLogVO> getFirearmsLog() {
        return firearmsApplyLogMapper.getFirearmsLog();
    }

    //领用记录详情
    @Override
    public List<FirearmsLogDetailVO> getFirearmsLogDetail(Integer id) {
        return firearmsApplyLogMapper.getFirearmsLogDetail(id);
    }


}
