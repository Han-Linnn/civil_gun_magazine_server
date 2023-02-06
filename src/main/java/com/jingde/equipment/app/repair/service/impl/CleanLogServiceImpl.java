package com.jingde.equipment.app.repair.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.app.firearms.dao.FirearmsMapper;
import com.jingde.equipment.app.record.dto.LableDTO;
import com.jingde.equipment.app.repair.dao.CleanLogInfoMapper;
import com.jingde.equipment.app.repair.dao.CleanLogMapper;
import com.jingde.equipment.app.repair.dto.CleanLogDTO;
import com.jingde.equipment.app.repair.dto.CleanLogInfoDTO;
import com.jingde.equipment.app.repair.service.CleanLogService;
import com.jingde.equipment.app.repair.vo.CleanLogInfoVo;
import com.jingde.equipment.app.repair.vo.CleanLogVo;
import com.jingde.equipment.app.system.dao.PermissionMapper;
import com.jingde.equipment.app.user.service.UserService;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.CleanLog;
import com.jingde.equipment.model.CleanLogInfo;
import com.jingde.equipment.model.Firearms;
import com.jingde.equipment.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.jingde.equipment.constant.ServiceConstant.*;

/**
 * @author
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CleanLogServiceImpl extends ServiceImpl<CleanLogMapper, CleanLog> implements CleanLogService {

    private final CleanLogMapper cleanLogMapper;

    private final CleanLogInfoMapper cleanLogInfoMapper;

    private final PermissionMapper permissionMapper;

    private final UserService userService;

    private final FirearmsMapper firearmsMapper;


    @Override
    public String selectStatusById(Integer id) {
        return cleanLogMapper.cleanStatusById(id);
    }

    /**
     * 新增擦拭保养记录
     *
     * @param form
     */
    @Override
    public void addCleanLog(User user, CleanLogDTO form) {
        int count = 0;
        List<CleanLogInfoDTO> list = form.getList();
        Iterator<CleanLogInfoDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            CleanLogInfoDTO dto = iterator.next();
            if ("0".equals(dto.getStatus())) {
                List<Firearms> firearmsList = firearmsMapper.selectApplyList(form.getCleanDate(),dto.getFirearmTypeId());
                if (null != firearmsList && firearmsList.size() > 0) {
                    String firearmsNos = firearmsList.stream().map(Firearms::getFirearmNo).collect(Collectors.joining());
                    //查询所有的枪支型号
                    dto.setFirearmNo(firearmsNos);
                    dto.setFirearmType(firearmsList.get(0).getFirearmType());
                } else {
                    iterator.remove();
                }
            }
            count += dto.getFirearmNo().split(",").length;
        }
        CleanLog cleanLog = new CleanLog();
        cleanLog.setApplyPerson(user.getPoliceName());
        cleanLog.setApplyPersonId(user.getId() + "");
        cleanLog.setCleanDate(form.getCleanDate());
        cleanLog.setResaon(form.getReason());
        cleanLog.setStatus(CLEAN_WAITING_AUDITING);
        cleanLog.setFirearmCount(count);
        try {
            int insert = cleanLogMapper.insert(cleanLog);
            if (insert > 0) {
                //保存擦拭保养详情
                cleanLogInfoMapper.insertList(cleanLog.getId(), list);
            }
        } catch (Exception e) {
            cleanLogMapper.deleteById(cleanLog.getId());
            throw new ServiceException("网络错误,请重新申请");
        }
    }

    /**
     * 擦拭保养编辑
     *
     * @param form
     */
    @Override
    public void updateCleanLog(CleanLogDTO form) {
        int count = 0;
        List<CleanLogInfoDTO> list = form.getList();
        Iterator<CleanLogInfoDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            CleanLogInfoDTO dto = iterator.next();
            if ("0".equals(dto.getStatus())) {
                QueryWrapper<Firearms> wrapper = new QueryWrapper();
                Map<String, Object> queryMap = new HashMap<>();
                //获取当时在库的所有枪支
                queryMap.put("status", "0");
                queryMap.put("firearm_type_id", dto.getFirearmTypeId());
                wrapper.allEq(queryMap);
                List<Firearms> firearmsList = firearmsMapper.selectList(wrapper);
                if (null != firearmsList && firearmsList.size() > 0) {
                    String firearmsNos = firearmsList.stream().map(Firearms::getFirearmNo).collect(Collectors.joining());
                    //查询所有的枪支型号
                    dto.setFirearmNo(firearmsNos);
                    dto.setFirearmType(firearmsList.get(0).getFirearmType());
                } else {
                    iterator.remove();
                }
            }
            count += dto.getFirearmNo().split(",").length;
        }
        CleanLog cleanLog = new CleanLog();
        cleanLog.setCleanDate(form.getCleanDate());
        cleanLog.setResaon(form.getReason());
        cleanLog.setStatus(CLEAN_WAITING_AUDITING);
        cleanLog.setFirearmCount(count);
        //更新记录
        cleanLogMapper.updateById(cleanLog);
        //更新擦拭详情
        //1删除原先
        QueryWrapper<CleanLogInfo> wrapper = new QueryWrapper();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("clean_log_id", form.getId());
        wrapper.allEq(queryMap);
        cleanLogInfoMapper.delete(wrapper);
        //保存擦拭保养详情
        cleanLogInfoMapper.insertList(cleanLog.getId(), list);
    }

    @Override
    public PageInfo listByPage(String status) {
        List<CleanLogVo> logVoList = new ArrayList<>();
        QueryWrapper<CleanLog> wrapper = new QueryWrapper();
        //不传状态,默认查询所有
        if (StringUtils.isNotBlank(status)) {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("status", status);
            wrapper.allEq(queryMap);
        }
        wrapper.orderByDesc("id");
        List<CleanLog> cleanLogs = cleanLogMapper.selectList(wrapper);
        if (null != cleanLogs && cleanLogs.size() > 0) {
            List<Integer> collect = cleanLogs.stream().map(CleanLog::getId).collect(Collectors.toList());
            //查询详情
            List<CleanLogInfoVo> cleanLogInfos = cleanLogInfoMapper.selectListMap(collect);
            for (CleanLog log : cleanLogs) {
                CleanLogVo logVo = new CleanLogVo();
                logVo.setId(log.getId());
                logVo.setCleanDate(log.getCleanDate());
                logVo.setReason(log.getResaon());
                logVo.setApplyPerson(log.getApplyPerson());
                logVo.setAuditingReason(log.getAuditingReason());
                logVo.setAuditingPerson(log.getAuditingPerson());
                logVo.setStatus(log.getStatus());
                if (null != log.getCleanPerson1()) {
                    logVo.setCleanPerson(log.getCleanPerson1() + "-" + log.getCleanPerson2());
                }
                if (null != log.getCleanCompletePerson1()) {
                    logVo.setCleanCompletePerson(log.getCleanCompletePerson1() + "-" + log.getCleanCompletePerson2());
                }
                Map<String, Integer> map = new HashMap<>();
                for (CleanLogInfoVo infoVo : cleanLogInfos) {
                    if (infoVo.getCleanLogId().equals(log.getId())) {
                        int length = infoVo.getFirearmNo().split(",").length;
                        map.put(infoVo.getFirearmType(), length);
                    }
                }
                logVo.setMap(map);
                logVoList.add(logVo);
            }
        }
        return new PageInfo(logVoList);
    }

    @Override
    public void updateByAuditing(CleanLogDTO dto) {
        //验证账号密码
        //验证密码
        Integer auditingPersonId = dto.getAuditingPersonId();
        String password = dto.getPassword();
        Map<String, String> map = new HashMap<>();
        map.put(String.valueOf(auditingPersonId), password);
        List<String> auditingPersonList = userService.verifyUserByAccount(map, null);
        String[] split = auditingPersonList.get(0).split("-");
        dto.setAuditingPersonId(Integer.parseInt(split[0]));
        dto.setAuditingPerson(split[1]);
        //3.验证审批人的权限
        int count = permissionMapper.selectItExistByUserIdAndPer(dto.getAuditingPersonId(), "cleanlog:cleanExamine");
        if (count <= 0) {
            throw new ServiceException("该账号无法审批");
        }
        //默认审批通过
        if (null == dto.getStatus()) {
            dto.setStatus(CLEAN_WAITING_CLEAN);
        }
        if (!CLEAN_WAITING_CLEAN.equals(dto.getStatus()) && !CLEAN_UN_AUDITING.equals(dto.getStatus())) {
            throw new ServiceException("不允许的状态操作");
        }
        cleanLogMapper.updateStatusById(dto);
    }

    @Override
    public void updateStartById(CleanLogDTO dto) {
        if (null == dto.getStatus()) {
            dto.setStatus(CLEAN_MAINTENANCE_CANCELLED);
        }
        //待保养变为保养中
        if (CLEAN_UNDER_MAINTENANCE.equals(dto.getStatus())) {
            //枪支状态变为保养中
            List<LableDTO> lableList = dto.getPerson();
            if (null == lableList || 2 != lableList.size()) {
                throw new ServiceException("保管人为空,或保管人不为两个人");
            }
            List<String> list = userService.selectUserByCode(lableList);
            if (2 != list.size()) {
                throw new ServiceException("需要两个保养人验证");
            }
            CleanLog log = new CleanLog();
            log.setId(dto.getId());
            String[] person = list.get(0).split("-");
            log.setCleanPerson1Id(person[0]);
            log.setCleanPerson1(person[1]);
            String[] person1 = list.get(1).split("-");
            log.setCleanPerson2Id(person1[0]);
            log.setCleanPerson2(person1[1]);
            log.setStatus(dto.getStatus());
            cleanLogMapper.updateById(log);
            //枪支状态变为保养中
            List<CleanLogInfoVo> cleanLogInfoVos = cleanLogInfoMapper.selectListByCleanLogId(String.valueOf(log.getId()));
            List<String> firearmsList = new ArrayList<>();
            for (CleanLogInfoVo infoVo : cleanLogInfoVos) {
                String[] split = infoVo.getFirearmNo().split(",");
                List<String> asList = Arrays.asList(split);
                firearmsList.addAll(asList);
            }
            int count = firearmsMapper.updateFirearmsStatus(firearmsList, 3, null);
        } else if (CLEAN_MAINTENANCE_COMPLETED.equals(dto.getStatus())) { //保养完成

            List<LableDTO> lableList = dto.getPerson();
            if (null == lableList || 2 != lableList.size()) {
                throw new ServiceException("保管人为空,或保管人不为两个人");
            }
            List<String> list = userService.selectUserByCode(lableList);
            if (2 != list.size()) {
                throw new ServiceException("需要两个保养人验证");
            }
            CleanLog log = new CleanLog();
            log.setId(dto.getId());
            String[] person = list.get(0).split("-");
            log.setCleanCompletePerson1Id(Integer.parseInt(person[0]));
            log.setCleanCompletePerson1(person[1]);
            String[] person1 = list.get(1).split("-");
            log.setCleanCompletePerson2Id(Integer.parseInt(person1[0]));
            log.setCleanCompletePerson2(person1[1]);
            log.setStatus(dto.getStatus());
            cleanLogMapper.updateById(log);
            //枪支状态修改为在库
            List<CleanLogInfoVo> cleanLogInfoVos = cleanLogInfoMapper.selectListByCleanLogId(String.valueOf(log.getId()));
            List<String> firearmsList = new ArrayList<>();
            for (CleanLogInfoVo infoVo : cleanLogInfoVos) {
                String[] split = infoVo.getFirearmNo().split(",");
                List<String> asList = Arrays.asList(split);
                firearmsList.addAll(asList);
            }
            int count = firearmsMapper.updateFirearmsStatus(firearmsList, 0, null);

        } else if (CLEAN_MAINTENANCE_CANCELLED.equals(dto.getStatus())) { //取消保养
            cleanLogMapper.updateStatusById(dto);
        } else {
            throw new ServiceException("不支持的状态操作");
        }


    }

    @Override
    public CleanLogVo findInfoById(String id) {
        CleanLogVo vo = new CleanLogVo();
        CleanLog cleanLog = cleanLogMapper.selectById(id);
        if (null != cleanLog) {
            List<CleanLogInfoVo> list = cleanLogInfoMapper.selectListByCleanLogId(id);
            vo.setId(cleanLog.getId());
            vo.setReason(cleanLog.getResaon());
            vo.setCleanDate(cleanLog.getCleanDate());
            vo.setApplyPerson(cleanLog.getApplyPerson());
            vo.setAuditingPerson(cleanLog.getAuditingPerson());
            vo.setAuditingReason(cleanLog.getAuditingReason());
            vo.setFirearmCount(cleanLog.getFirearmCount());
            vo.setStatus(cleanLog.getStatus());
            if (null != cleanLog.getCleanPerson1()) {
                vo.setCleanPerson(cleanLog.getCleanPerson1() + "-" + cleanLog.getCleanPerson2());
            }
            if (null != cleanLog.getCleanCompletePerson1()) {
                vo.setCleanCompletePerson(cleanLog.getCleanCompletePerson1() + "-" + cleanLog.getCleanCompletePerson2());
            }
            vo.setList(list);
        }
        return vo;
    }

}
