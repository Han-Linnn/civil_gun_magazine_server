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
            throw new ServiceException("??????????????????????????????");
        }
        if (StringUtils.isBlank(form.getReturnDate())) {
            throw new ServiceException("????????????????????????");
        }
        //????????????,???????????????id
        firearmsApplyLogMapper.insertByForm(form);
        if (null == form.getId()) {
            Integer id = firearmsApplyLogMapper.selectMaxId();
            form.setId(id);
        }
        //???????????????????????????
        List<FirearmsApplyTypeLogDTO> list = form.getList();
        if (null == list || 0 >= list.size()) {
            throw new ServiceException("?????????????????????????????????");
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
                throw new ServiceException("????????????????????????");
            }
            if (null == typeDto.getApplyAmmoCount()) {
                typeDto.setApplyAmmoCount(0);
            }
            if (null == typeDto.getFirearmTypeId()) {
                throw new ServiceException("????????????id??????");
            }
            //??????????????????????????????????????????
            for (FirearmsTypeVO typeVo : firearmsTypeVOS) {
                if (typeDto.getFirearmTypeId().equals(typeVo.getFirearmsTypeId())
                        && typeDto.getApplyFirearmCount() > typeVo.getCanApply()) {
                    throw new ServiceException("???????????????????????????????????????????????????");
                }
            }
            FirearmsType firearmsType = firearmsTypeMapper.selectById(typeDto.getFirearmTypeId());
            if (null == firearmsType) {
                throw new ServiceException("?????????????????????,???????????????");
            }
            typeDto.setFirearmType(firearmsType.getFirearmsType());
            //?????????????????????
            if (null != typeDto.getAmmoTypeId()) {
                AmmoType ammoType = ammoTypeMapper.selectById(typeDto.getAmmoTypeId());
                if (null == ammoType) {
                    throw new ServiceException("?????????????????????,???????????????");
                }
                if (null != typeDto.getApplyAmmoCount() && ammoType.getNumber() < typeDto.getApplyAmmoCount()) {
                    throw new ServiceException("[" + ammoType.getAmmoType() + "]??????????????????????????????");
                }
                typeDto.setAmmoType(ammoType.getAmmoType());
            }
        }
        firearmsApplyTypeLogMapper.insertByList(list);
    }

    @Override
    public void updateById(User user, FirearmsApplyLogForm form) {
        //????????????01,02,08????????????,??????????????????01,?????????
        FirearmsApplyLog firearmsApplyLog = firearmsApplyLogMapper.selectById(form.getId());
        if (!firearmsApplyLog.getApplyPersonId().equals(user.getId())) {
            throw new ServiceException("????????????(??????????????????????????????)");
        }
        if (!APPLY_STATUS_EXAMINE.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_RECEIVE.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_CANCEL.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("????????????,?????????????????????");
        }
        if (StringUtils.isBlank(form.getApplyDate())) {
            throw new ServiceException("??????????????????????????????");
        }
        if (StringUtils.isBlank(form.getReturnDate())) {
            throw new ServiceException("????????????????????????");
        }
        form.setStatus(APPLY_STATUS_EXAMINE);
        form.setAuditingPerson(null);
        form.setAuditingPersonId(null);
        form.setNote(null);
        //??????????????????
        firearmsApplyLogMapper.updateFormById(form);
        //?????????????????????????????????
        firearmsApplyTypeLogMapper.deleteByLogId(form.getId());
        //???????????????????????????
        List<FirearmsApplyTypeLogDTO> list = form.getList();
        if (null == list || 0 >= list.size()) {
            throw new ServiceException("?????????????????????????????????");
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
                throw new ServiceException("????????????????????????");
            }
            if (null == typeDto.getFirearmTypeId()) {
                throw new ServiceException("????????????id??????");
            }
            //??????????????????????????????????????????
            for (FirearmsTypeVO typeVo : firearmsTypeVOS) {
                if (typeDto.getFirearmTypeId().equals(typeVo.getFirearmsTypeId())
                        && typeDto.getApplyFirearmCount() > typeVo.getCanApply()) {
                    throw new ServiceException("???????????????????????????????????????????????????");
                }
            }
            if (null == typeDto.getApplyAmmoCount()) {
                typeDto.setApplyAmmoCount(0);
            }
            FirearmsType firearmsType = firearmsTypeMapper.selectById(typeDto.getFirearmTypeId());
            if (null == firearmsType) {
                throw new ServiceException("?????????????????????,???????????????");
            }
            typeDto.setFirearmType(firearmsType.getFirearmsType());
            //?????????????????????
            if (null != typeDto.getAmmoTypeId()) {
                AmmoType ammoType = ammoTypeMapper.selectById(typeDto.getAmmoTypeId());
                if (null == ammoType) {
                    throw new ServiceException("?????????????????????,???????????????");
                }
                if (null != typeDto.getApplyAmmoCount() && ammoType.getNumber() < typeDto.getApplyAmmoCount()) {
                    throw new ServiceException("[" + ammoType.getAmmoType() + "]??????????????????????????????");
                }
                typeDto.setAmmoType(ammoType.getAmmoType());
            }
        }
        firearmsApplyTypeLogMapper.insertByList(list);
    }

    @Override
    public void updateApprovalById(HttpServletRequest request,User user, FirearmsApplyLogForm form) {
        //????????????
        Integer auditingPersonId = form.getAuditingPersonId();
        String password = form.getAuditingPassword();
        Map<String, String> map = new HashMap<>();
        map.put(String.valueOf(auditingPersonId), password);
        List<String> auditingPersonList = userService.verifyUserByAccount(map, null);
        String[] split = auditingPersonList.get(0).split("-");
        form.setAuditingPersonId(Integer.parseInt(split[0]));
        form.setAuditingPerson(split[1]);
        //???????????????????????????
        int itExist = permissionMapper.selectItExistByUserIdAndPer(auditingPersonId, "applyLog:examine");
        if (itExist <= 0) {
            throw new ServiceException("??????????????????");
        }
        if (!APPLY_STATUS_RECEIVE.equals(form.getStatus()) && !APPLY_STATUS_NO_EXAMINE.equals(form.getStatus())) {
            throw new ServiceException("????????????????????????");
        }
        FirearmsApplyLog firearmsApplyLog = firearmsApplyLogMapper.selectById(form.getId());
        if (!APPLY_STATUS_EXAMINE.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("?????????????????????????????????");
        }
        //??????
        firearmsApplyLogMapper.updateApprovalById(form);

        //??????????????????
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
            throw new ServiceException("????????????,?????????????????????");
        }
        if (APPLY_STATUS_RECEIVE_NOT_RETURN.equals(status)
                && !APPLY_STATUS_RECEIVE_ING.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("????????????,?????????????????????????????????????????????");
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
        //?????????????????????
        List<Integer> ids = list.stream().map(FirearmsApplyLogVo::getId).collect(Collectors.toList());
        //???????????????????????????
        List<FirearmsReceiveBatchVo> receiveBatchVos = firearmsReceiveBatchMapper.selectByApplyIds(ids);
        //???????????????????????????
        List<FirearmsReturnBatchVo> returnBatchVos = firearmsReturnBatchMapper.selectByApplyIds(ids);
        //??????????????????????????????
        List<FirearmsApplyTypeLogVo> firearmsApplyTypeLogVos = firearmsApplyTypeLogMapper.selectByApplyIds(ids);

        for (FirearmsApplyLogVo logVo : list) {
            //????????????
            List<FirearmsReceiveBatchVo> resltReceiveBatchVos = new ArrayList<>();
            //????????????
            List<FirearmsReturnBatchVo> resltReturnBatchVos = new ArrayList<>();
            //???????????????????????????
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
        //????????????,???????????????,????????????
        FirearmsApplyLogVo firearmsApplyLog = firearmsApplyLogMapper.findInfoById(id);
        if (null == firearmsApplyLog) {
            return new FirearmsApplyLogVo();
        }
        //???????????????????????????
        List<FirearmsReceiveBatchVo> receiveBatchVos = firearmsReceiveBatchMapper.selectByApplyId(id);
        //???????????????????????????
        List<FirearmsReturnBatchVo> returnBatchVos = firearmsReturnBatchMapper.selectByApplyId(id);
        //?????????????????????
        List<FirearmsApplyTypeLogVo> types = firearmsApplyTypeLogMapper.selectByApplyId(id);
        //?????????????????????
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
            throw new ServiceException("??????????????????");
        }
        if (!APPLY_STATUS_RECEIVE.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_RECEIVE_ING.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("???????????????????????????");
        }
        //???????????????,?????????
        //???????????????
        Integer confirmPersonId = dto.getConfirmPersonId();
        String password = dto.getConfirmPersonPassword();
        Map<String, String> map = new HashMap<>();
        map.put(String.valueOf(confirmPersonId), password);
        List<String> auditingPersonList = userService.verifyUserByAccount(map, null);
        String[] confirmPerson = auditingPersonList.get(0).split("-");
        dto.setConfirmPersonId(Integer.parseInt(confirmPerson[0]));
        dto.setConfirmPerson(confirmPerson[1]);
        //???????????????
        List<String> list = userService.selectUserByCode(dto.getLableList());
        String[] split = list.get(0).split("-");
        dto.setKeepingPersonId(Integer.parseInt(split[0]));
        dto.setKeepingPerson(split[1]);
        String[] split1 = list.get(1).split("-");
        dto.setKeepingPerson2Id(Integer.parseInt(split1[0]));
        dto.setKeepingPerson2(split1[1]);
        //???????????????????????????
        int itExist = permissionMapper.selectItExistByUserIdAndPer(dto.getKeepingPersonId(), "firearms:scanOut");
        if (itExist <= 0) {
            throw new ServiceException("[" + dto.getKeepingPerson() + "]" + "???????????????");
        }
        int itExist2 = permissionMapper.selectItExistByUserIdAndPer(dto.getKeepingPerson2Id(), "firearms:scanOut");
        if (itExist2 <= 0) {
            throw new ServiceException("[" + dto.getKeepingPerson2() + "]" + "???????????????");
        }
        //??????????????????
        firearmsReceiveBatchMapper.insertByDto(dto);
        Integer id = dto.getId();
        if (null == id) {
            id = firearmsReceiveBatchMapper.selectMaxId();
        }
        //??????????????????
        List<FirearmsApplyTypeLogDTO> types = dto.getTypes();
        //????????????????????????
        List<FirearmsApplyTypeInfoDTO> typeInfos = dto.getInfos();
        //???????????????????????????
        List<FirearmsApplyTypeLogVo> firearmsApplyTypeLogVos = firearmsApplyTypeLogMapper.selectByApplyId(dto.getApplyId());
        boolean flag = true;
        //??????????????????
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
                        throw new ServiceException("?????????[" + logVo.getFirearmType() + "]?????????????????????????????????");
                    }
                    if (logVo.getApplyAmmoCount() < type.getReceivedAmmoCount() + logVo.getReceivedAmmoCount()) {
                        throw new ServiceException("?????????[" + logVo.getFirearmType() + "]???????????????????????????????????????");
                    }
                    //??????????????????,??????????????????????????????
                    if (logVo.getApplyFirearmCount() > type.getReceivedFirearmCount() + logVo.getReceivedFirearmCount()) {
                        flag = false;
                    }
                    if (logVo.getApplyAmmoCount() > type.getReceivedAmmoCount() + logVo.getReceivedAmmoCount()) {
                        flag = false;
                    }
                }
            }
        }
        //??????????????????
        firearmsApplyTypeInfoMapper.insertByList(typeInfos);
        List<String> firearmsIds = typeInfos.stream().map(FirearmsApplyTypeInfoDTO::getFirearmNo).collect(Collectors.toList());
        //??????????????????????????????
        firearmsMapper.updateFirearmsStatus(firearmsIds, 1, firearmsApplyLog.getReturnDate());
        //??????????????????
        firearmsApplyTypeLogMapper.updateReceivedCountByIds(types);
        //???????????????????????????
        types.removeIf(next -> next.getReceivedAmmoCount() == 0);
        if (types.size() > 0) {
            //????????????????????????
            ammoReceiveBatchInfoMapper.insertByList(types);
        }
        if (ammoList.size() > 0) {
            //??????????????????(??????)
            ammoTypeMapper.updateReduceCountByList(ammoList);
        }
        // ??????flag = true
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
            throw new ServiceException("??????????????????");
        }
        if (!APPLY_STATUS_ALL_RECEIVE_NOT_RETURN.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_RECEIVE_NOT_RETURN.equals(firearmsApplyLog.getStatus())
                && !APPLY_STATUS_PART_RETURN.equals(firearmsApplyLog.getStatus())) {
            throw new ServiceException("???????????????????????????");
        }
        //???????????????,?????????
        //???????????????
        Integer auditingPersonId = dto.getConfirmPersonId();
        String password = dto.getConfirmPersonPassword();
        Map<String, String> map = new HashMap<>();
        map.put(String.valueOf(auditingPersonId), password);
        List<String> auditingPersonList = userService.verifyUserByAccount(map, null);
        String[] confirmPerson = auditingPersonList.get(0).split("-");
        dto.setConfirmPersonId(Integer.parseInt(confirmPerson[0]));
        dto.setConfirmPerson(confirmPerson[1]);
        //???????????????
        List<String> list = userService.selectUserByCode(dto.getLableList());
        String[] split = list.get(0).split("-");
        dto.setKeepingPersonId(Integer.parseInt(split[0]));
        dto.setKeepingPerson(split[1]);
        String[] split1 = list.get(1).split("-");
        dto.setKeepingPerson2Id(Integer.parseInt(split1[0]));
        dto.setKeepingPerson2(split1[1]);
        //???????????????????????????
        int itExist = permissionMapper.selectItExistByUserIdAndPer(dto.getKeepingPersonId(), "firearms:scanCome");
        if (itExist <= 0) {
            throw new ServiceException("[" + dto.getKeepingPerson() + "]" + "???????????????");
        }
        int itExist2 = permissionMapper.selectItExistByUserIdAndPer(dto.getKeepingPerson2Id(), "firearms:scanCome");
        if (itExist2 <= 0) {
            throw new ServiceException("[" + dto.getKeepingPerson2() + "]" + "???????????????");
        }
        //??????????????????
        firearmsReturnBatchMapper.insertByDto(dto);
        Integer id = dto.getId();
        if (null == id) {
            id = firearmsReturnBatchMapper.selectMaxId();
        }
        //????????????????????????
        List<FirearmsApplyTypeLogDTO> types = dto.getTypes();
        //??????????????????????????????
        List<FirearmsApplyTypeInfoDTO> typeInfos = dto.getInfos();
        //???????????????????????????
        List<FirearmsApplyTypeLogVo> firearmsApplyTypeLogVos = firearmsApplyTypeLogMapper.selectByApplyId(dto.getApplyId());

        //??????????????????????????????????????????,??????????????????????????????
        boolean flag = true;
        //??????????????????
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
                    //????????????id
                    info.setApplyTypeId(type.getId());
                }
                //?????????????????????id
                info.setReturnBatchId(id);
                //????????????id
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
                        throw new ServiceException("?????????[" + logVo.getFirearmType() + "]????????????????????????????????????");
                    }
                    if (logVo.getReceivedAmmoCount() < type.getReturnedAmmoCount() + logVo.getReturnedAmmoCount()) {
                        throw new ServiceException("?????????[" + logVo.getFirearmType() + "]??????????????????????????????????????????");
                    }
                }
                //??????????????????,??????????????????????????????
                if (logVo.getReceivedFirearmCount() > type.getReturnedFirearmCount() + logVo.getReturnedFirearmCount()) {
                    flag = false;
                }
            }
        }
        //???????????????????????????(?????????)
        firearmsApplyTypeInfoMapper.updateListByIds(typeInfos);

        //???????????????????????????
        List<String> firearmsIds = typeInfos.stream().map(FirearmsApplyTypeInfoDTO::getFirearmNo).collect(Collectors.toList());
        firearmsMapper.updateFirearmsStatus(firearmsIds, 0, null);
        //??????????????????
        firearmsApplyTypeLogMapper.updateReturnCountByIds(types);
        //???????????????????????????
        types.removeIf(next -> next.getReturnedAmmoCount() == 0);
        if (types.size() > 0) {
            //????????????????????????
            ammoReturnBatchInfoMapper.insertByList(types);
        }
        if (ammoList.size() > 0) {
            //??????????????????(??????)
            ammoTypeMapper.updateIncreaseCountByList(ammoList);
        }
        //????????????????????????,?????????????????????,????????????????????????
        if (flag) { //????????????,???????????????????????????
            firearmsApplyLogMapper.updateStatusById(dto.getApplyId(), APPLY_STATUS_COMPLETE);
        } else { //????????????,??????????????????????????????
            firearmsApplyLogMapper.updateStatusById(dto.getApplyId(), APPLY_STATUS_PART_RETURN);
        }

    }

    @Override
    public List<FirearmsLogVO> getFirearmsLog() {
        return firearmsApplyLogMapper.getFirearmsLog();
    }

    //??????????????????
    @Override
    public List<FirearmsLogDetailVO> getFirearmsLogDetail(Integer id) {
        return firearmsApplyLogMapper.getFirearmsLogDetail(id);
    }


}
