package com.jingde.equipment.app.cabinets.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.cabinets.dao.CabinetDetailMapper;
import com.jingde.equipment.app.cabinets.dao.CabinetOpenLogMapper;
import com.jingde.equipment.app.cabinets.dto.CabinetAddDTO;
import com.jingde.equipment.app.cabinets.dto.CabinetSeatDTO;
import com.jingde.equipment.app.cabinets.dto.CabinetSeatListDTO;
import com.jingde.equipment.app.cabinets.dao.CabinetSeatLogMapper;
import com.jingde.equipment.app.cabinets.vo.CabinetOpenLogVO;
import com.jingde.equipment.model.*;
import com.jingde.equipment.util.RedisUtil;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.app.cabinets.dao.CabinetMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author
 */
@Service
public class CabinetService extends ServiceImpl<CabinetMapper, Cabinet> implements IService<Cabinet> {
    private final Logger logger = LoggerFactory.getLogger(CabinetService.class);
    @Resource
    private CabinetMapper cabinetMapper;
    @Resource
    private CabinetDetailMapper cabinetDetailMapper;
    @Resource
    private CabinetTypeService cabinetTypeService;
    @Resource
    private CabinetSeatService cabinetSeatService;
    @Resource
    private CabinetOpenLogService openLogService;
    @Resource
    private CabinetOpenLogMapper openLogMapper;
    @Resource
    private CabinetSeatLogMapper seatLogMapper;
    @Resource
    private CabinetSeatLogService seatLogService;
    @Resource
    private RedisUtil redisUtil;
    @Value("${app.hardware.prefixUrl}")
    private String hardwareUrl;

    /**
     * ????????????
     *
     * @return
     */
    public List<Cabinet> cabinetList(String status) {
        if (StringUtils.isBlank(status) || "1".equals(status)) {
            return list();
        } else {
            QueryWrapper<Cabinet> queryWrapper = new QueryWrapper<>();
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("status", 1);
            queryMap.put("sync_status", 1);
            queryWrapper.allEq(queryMap);
            queryWrapper.orderBy(true,false, "is_commonly");
            return list(queryWrapper);
        }

    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @return
     */
    public List<Map> detailCabinetList() {
        // ??????????????????
        QueryWrapper<Cabinet> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("status", 1);
        queryMap.put("sync_status", 1);
        queryWrapper.allEq(queryMap);
        List<Cabinet> cabinets = cabinetMapper.selectList(queryWrapper);
        List<Map> cabinetMapList = new ArrayList<>();
        for (Cabinet cabinet : cabinets) {
            Map<String, Object> cabinetMap = (Map) JSON.toJSON(cabinet);
            cabinetMapList.add(cabinetMap);
        }
        // ????????????????????????????????????
        List<Map> detailCabinetList = cabinetMapper.detailCabinetList();
        // ????????????????????????????????????
        List<Map> ammoCabinetList = cabinetMapper.detailWithAmmoCabinetList();
        // ????????????????????????
        if (detailCabinetList != null && detailCabinetList.size() > 0) {
            if (ammoCabinetList != null && ammoCabinetList.size() > 0) {
                for (Map cabinetMap : cabinetMapList) {
                    // ???????????????????????????
                    ArrayList<Map> itemList = new ArrayList<>();
                    Integer id = (Integer) cabinetMap.get("id");
                    for (Map detailCabinet : detailCabinetList) {
                        Integer cabinetId = (Integer) detailCabinet.get("cabinetId");
                        if (cabinetId.equals(id)) {
                            HashMap<String, Object> item = new HashMap<>();
                            String firearmType = (String) detailCabinet.get("firearmType");
                            Object count = detailCabinet.get("count");
                            item.put("type", firearmType);
                            item.put("count", count);
                            itemList.add(item);
                        }
                    }
                    for (Map ammoCabinet : ammoCabinetList) {
                        Object cabinetId = ammoCabinet.get("cabinetId");
                        if (cabinetId.equals(id)) {
                            HashMap<String, Object> item = new HashMap<>();
                            String ammoType = (String) ammoCabinet.get("ammoType");
                            Object count = ammoCabinet.get("count");
                            item.put("type", ammoType);
                            item.put("count", count);
                            itemList.add(item);
                        }
                    }
                    cabinetMap.put("itemList", itemList);
                }
            }
        }
        return cabinetMapList;
    }

    /**
     * ????????????
     *
     * @param form
     */
    public void insertCabinet(String registerPerson, CabinetAddDTO form) {
        if (null == form.getIsCommonly() || (1 != form.getIsCommonly() && 0 != form.getIsCommonly()))
            form.setIsCommonly(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String registerDate = df.format(new Date());
        Cabinet cabinetModel = new Cabinet();
        BeanUtils.copyProperties(form, cabinetModel);
        cabinetModel.setRegisterPerson(registerPerson);
        cabinetModel.setRegisterDate(registerDate);
        save(cabinetModel);
    }

    /**
     * ????????????????????????
     *
     * @param form
     */
    public void updateCabinet(String registerPerson, CabinetAddDTO form) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String registerDate = df.format(new Date());
        Cabinet cabinetModel = new Cabinet();
        BeanUtils.copyProperties(form, cabinetModel);
        cabinetModel.setRegisterPerson(registerPerson);
        cabinetModel.setRegisterDate(registerDate);
        updateById(cabinetModel);
    }

    /**
     * ?????????????????????
     *
     * @param cabinetId
     * @param modifier
     */
    @Transactional
    public void syncCabinetInfo(String modifier, Integer cabinetId) throws Exception {
        Cabinet cabinet = cabinetMapper.selectById(cabinetId);
        String ip = cabinet.getIp();
        String cabinetCode = cabinet.getCabinetCode();
        // ??????????????????
        String url = String.format("%s/api/locker/info?ip=%s&dynamicCode=%s", hardwareUrl, ip, cabinetCode);
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseStr = response.body().string();
            Map<String, Object> resMap = (Map<String, Object>) JSONObject.parse(responseStr);
            Integer state = (Integer) resMap.get("state");
            if (state.equals(1000)) {
                JSONObject data = (JSONObject) resMap.get("data");
                String cabinetEntityId = (String) data.get("@id");
                String cabinetIndex = (String) data.get("@index");
                String cabinetLayout = (String) data.get("@layout");
                String cabinetHeight = (String) data.get("@height");
                String cabinetWidth = (String) data.get("@width");
                cabinet.setCabinetEntityId(cabinetEntityId);
                cabinet.setCabinetIndex(Integer.parseInt(cabinetIndex));
                cabinet.setCabinetLayout(cabinetLayout);
                cabinet.setCabinetHeight(Integer.parseInt(cabinetHeight));
                cabinet.setCabinetWidth(Integer.parseInt(cabinetWidth));
                CabinetDetail cabinetDetail = new CabinetDetail();
                cabinetDetail.setCabinetId(cabinetId);
                cabinetDetail.setCabinetJson(JSON.toJSONString(data));
                // ??????????????????
                cabinetDetailMapper.insert(cabinetDetail);
                // ????????????????????????
                ArrayList<CabinetSeat> cabinetSeats = new ArrayList<>();
                // ??????????????????
                JSONArray doorList = (JSONArray) data.get("door");
                for (Object doorObj : doorList) {
                    JSONObject door = (JSONObject) doorObj;
                    // ????????????
                    JSONArray areaList = (JSONArray) door.get("area");
                    for (Object areaObj : areaList) {
                        JSONObject area = (JSONObject) areaObj;
                        // ????????????
                        JSONArray subAreaList = (JSONArray) area.get("area");
                        // ???????????????
                        if (subAreaList != null && subAreaList.size() > 0) {
                            for (Object subAreaObj : subAreaList) {
                                JSONObject subArea = (JSONObject) subAreaObj;
                                saveCabinetSeat(cabinetSeats, cabinetId, subArea);
                            }
                        }
                        // ??????????????????
                        else {
                            saveCabinetSeat(cabinetSeats, cabinetId, area);
                        }
                    }
                }
                // ??????????????????
                cabinetSeatService.saveBatch(cabinetSeats);
            } else {
                throw new ServiceException("???????????????????????????");
            }
        }
        // ????????????????????????
        cabinet.setSyncStatus(1);
        cabinetMapper.updateById(cabinet);
    }

    /**
     * ????????????(??????????????????)
     *
     * @param form
     */
    @Transactional
    public void updateCabinetSeat(CabinetSeatListDTO form) {
        Integer needSaveLog = form.getNeedSaveLog();
        // ??????????????????
        List<CabinetSeatDTO> cabinetSeats = form.getCabinetSeats();
        for (CabinetSeatDTO formSeat : cabinetSeats) {
            String seatEntityId = formSeat.getSeatEntityId();
            Integer status = formSeat.getStatus();
            QueryWrapper<CabinetSeat> updateWrapper = new QueryWrapper<>();
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("seat_entity_id", seatEntityId);
            updateWrapper.allEq(queryMap);
            CabinetSeat cabinetSeatModel = new CabinetSeat();
            BeanUtils.copyProperties(formSeat, cabinetSeatModel);
            // TODO: ???????????????????????????
            if (null == status) {
                // ??????????????? ????????? && ??????
                cabinetSeatModel.setStatus(1);
            }
            cabinetSeatService.update(cabinetSeatModel, updateWrapper);
            if (null != status && status == 1) {
                // ???????????????????????????????????????????????????????????????
                seatLogMapper.updateBackStatus(seatEntityId, 1);
            }
        }
        // ??????????????????json??????
        Integer cabinetId = form.getCabinetId();
        QueryWrapper<CabinetDetail> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("cabinet_id", cabinetId);
        queryWrapper.allEq(queryMap);
        CabinetDetail cabinetDetail = cabinetDetailMapper.selectOne(queryWrapper);
        String cabinetJson = cabinetDetail.getCabinetJson();
        JSONObject data = (JSONObject) JSONObject.parse(cabinetJson);
        JSONArray doorList = (JSONArray) data.get("door");
        for (Object doorObj : doorList) {
            JSONObject door = (JSONObject) doorObj;
            // ????????????
            JSONArray areaList = (JSONArray) door.get("area");
            for (Object areaObj : areaList) {
                JSONObject area = (JSONObject) areaObj;
                // ????????????
                JSONArray subAreaList = (JSONArray) area.get("area");
                // ???????????????
                if (subAreaList != null && subAreaList.size() > 0) {
                    for (Object subAreaObj : subAreaList) {
                        JSONObject subArea = (JSONObject) subAreaObj;
                        editCabinetSeat(form, subArea);
                    }
                }
                // ??????????????????
                else {
                    editCabinetSeat(form, area);
                }
            }
        }
        cabinetJson = JSON.toJSONString(data);
        // ????????????????????????
        cabinetDetail.setCabinetJson(cabinetJson);
        cabinetDetailMapper.updateById(cabinetDetail);
        // ??????????????????
        if (needSaveLog != null && needSaveLog.equals(1)) {
            saveTakeArmLog(form);
        }
    }

    /**
     * ???????????? (????????????????????????)
     *
     * @param form
     * @throws Exception
     */
    public void cabinetOpenWithStatus(CabinetSeatListDTO form) throws Exception {
        this.cabinetOpen(form);
        this.updateCabinetSeat(form);
    }

    /**
     * ????????????
     *
     * @param form
     */
    public void cabinetOpen(CabinetSeatListDTO form) throws Exception {
        Integer cabinetId = form.getCabinetId();
        List<CabinetSeatDTO> cabinetSeats = form.getCabinetSeats();
        Cabinet cabinet = cabinetMapper.selectById(cabinetId);
        String ip = cabinet.getIp();
        String cabinetCode = cabinet.getCabinetCode();
        // redis ?????????????????????
        HashMap<String, Object> redisMap = new HashMap<>();
        ArrayList<String> weaponList = new ArrayList<>();
        ArrayList<String> bulletList = new ArrayList<>();
        for (CabinetSeatDTO cabinetSeat : cabinetSeats) {
            String seatType = cabinetSeat.getSeatType();
            // ??????
            if (seatType.equals("bulletDrawerSeat")) {
                String seatEntityId = cabinetSeat.getSeatEntityId();
                bulletList.add(seatEntityId);
            }
            // ??????
            else {
                String seatEntityId = cabinetSeat.getSeatEntityId();
                weaponList.add(seatEntityId);
            }
        }
        redisMap.put("weapon", weaponList);
        redisMap.put("bullet", bulletList);
        if (redisUtil.set(cabinetCode, redisMap)) {
            // ????????????
            String url = String.format("%s/api/locker/notify", hardwareUrl);
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            HashMap<String, Object> reqMap = new HashMap<>();
            reqMap.put("ip", ip);
            reqMap.put("dynamicCode", cabinetCode);
            reqMap.put("cabinetId", cabinetId);
            String reqString = JSON.toJSONString(reqMap);
            RequestBody requestBody = RequestBody.create(mediaType, reqString);
            // ?????????????????????????????????
            Request request = new Request.Builder().url(url).post(requestBody).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseStr = response.body().string();
                Map<String, Object> resMap = (Map<String, Object>) JSONObject.parse(responseStr);
                Integer state = (Integer) resMap.get("state");
                if (!state.equals(1000)) {
                    throw new ServiceException("??????????????????");
                }
            }
        } else {
            throw new ServiceException("??????????????????");
        }
    }

    /**
     * ????????????
     *
     * @param cabinetId
     */
    @Transactional
    public void deleteCabinet(Integer cabinetId) {
        // ????????????????????????
        cabinetMapper.deleteById(cabinetId);
        // ??????????????????
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("cabinet_id", cabinetId);
        cabinetDetailMapper.deleteByMap(queryMap);
        // ??????????????????
        cabinetSeatService.removeByMap(queryMap);
    }

    /**
     * ????????????
     *
     * @param cabinetId
     * @return
     */
    public Map<String, Object> cabinetDetail(Integer cabinetId) {
        // ????????????
        Cabinet cabinet = cabinetMapper.selectById(cabinetId);
        // ????????????
        QueryWrapper<CabinetDetail> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("cabinet_id", cabinetId);
        queryWrapper.allEq(queryMap);
        CabinetDetail cabinetDetail = cabinetDetailMapper.selectOne(queryWrapper);
        // ????????????
        Map<String, Object> cabinetInfo = (Map<String, Object>) JSON.toJSON(cabinet);
        if (cabinetDetail != null) {
            String cabinetJson = cabinetDetail.getCabinetJson();
            Map<String, Object> cabinetData = (Map<String, Object>) JSONObject.parse(cabinetJson);
            cabinetData.put("cabinetInfo", cabinetInfo);
            return cabinetData;
        }
        HashMap<String, Object> cabinetData = new HashMap<>();
        cabinetData.put("cabinetInfo", cabinetInfo);
        return cabinetData;
    }

    /**
     * ????????????/??????????????????
     *
     * @param type
     */
    public List<Cabinet> cabinetSelect(Integer type) {
        if (null == type) {
            type = 0;
        }
        return cabinetMapper.cabinetSelect(type);
    }

    /**
     * ??????????????????
     */
    public List<CabinetOpenLogVO> cabinetOpenLogList() {
        return openLogMapper.cabinetOpenLogList();
    }

    // ===================================

    /**
     * ??????????????????
     */
    private void saveTakeArmLog(CabinetSeatListDTO form) {
        Integer cabinetId = form.getCabinetId();
        String cabinetEntityId = form.getCabinetEntityId();
        String seqNo = form.getSeqNo();
        // ??????????????????
        CabinetOpenLog cabinetOpenLogModel = new CabinetOpenLog();
        cabinetOpenLogModel.setCabinetId(cabinetId);
        cabinetOpenLogModel.setCabinetEntityId(cabinetEntityId);
        cabinetOpenLogModel.setSeqNo(seqNo);
        // ???????????????0 ???????????? 1 ???????????? 2 ????????????
        cabinetOpenLogModel.setActionType(1);
        openLogService.save(cabinetOpenLogModel);
        Integer iopenLogId = cabinetOpenLogModel.getId();
        // ??????????????????
        List<CabinetOpenSeatLog> cabinetOpenLogs = new ArrayList<>();
        List<CabinetSeatDTO> cabinetSeats = form.getCabinetSeats();
        for (CabinetSeatDTO cabinetSeatDTO : cabinetSeats) {
            Integer takeCount = cabinetSeatDTO.getTakeCount();
            String seatType = cabinetSeatDTO.getSeatType();
            CabinetOpenSeatLog openSeatLogModel = new CabinetOpenSeatLog();
            BeanUtils.copyProperties(cabinetSeatDTO, openSeatLogModel);
            if (seatType.equals("bulletDrawerSeat") && takeCount != null && takeCount > 0) {
                openSeatLogModel.setAmmoCount(takeCount);
            }
            openSeatLogModel.setOpenLogId(iopenLogId);
            cabinetOpenLogs.add(openSeatLogModel);
        }
        if (cabinetOpenLogs.size() > 0) {
            seatLogService.saveBatch(cabinetOpenLogs);
        }
    }

    /**
     * ??????????????????
     *
     * @param cabinetSeats
     * @param cabinetId
     * @param area
     */
    private void saveCabinetSeat(ArrayList<CabinetSeat> cabinetSeats, Integer cabinetId, JSONObject area) {
        // ??????
        JSONArray seatList = (JSONArray) area.get("shortGunSeat");
        String seatType = "shortGunSeat";
        if (seatList == null) {
            // ??????
            seatList = (JSONArray) area.get("longGunSeat");
            seatType = "longGunSeat";
        }
        if (seatList == null) {
            // ??????
            seatList = (JSONArray) area.get("bulletDrawerSeat");
            seatType = "bulletDrawerSeat";
        }
        for (Object seatObj : seatList) {
            JSONObject seat = (JSONObject) seatObj;
            String seatEntityId = (String) seat.get("@id");
            String seatEntityIndex = (String) seat.get("@index");
            CabinetSeat cabinetSeatModel = new CabinetSeat();
            cabinetSeatModel.setCabinetId(cabinetId);
            cabinetSeatModel.setSeatType(seatType);
            cabinetSeatModel.setSeatEntityId(seatEntityId);
            cabinetSeatModel.setSeatEntityIndex(seatEntityIndex);
            cabinetSeats.add(cabinetSeatModel);
        }
    }

    /**
     * ??????????????????
     *
     * @param form
     * @param area
     */
    private void editCabinetSeat(CabinetSeatListDTO form, JSONObject area) {
        // ??????
        JSONArray seatList = (JSONArray) area.get("shortGunSeat");
        if (seatList == null) {
            // ??????
            seatList = (JSONArray) area.get("longGunSeat");
        }
        if (seatList == null) {
            // ??????
            seatList = (JSONArray) area.get("bulletDrawerSeat");
        }
        for (Object seatObj : seatList) {
            JSONObject seat = (JSONObject) seatObj;
            List<CabinetSeatDTO> cabinetSeats = form.getCabinetSeats();
            for (CabinetSeatDTO cabinetSeat : cabinetSeats) {
                String formSeatEntityId = cabinetSeat.getSeatEntityId();
                String firearmType = cabinetSeat.getFirearmType();
                String firearmNo = cabinetSeat.getFirearmNo();
                Integer firearmTypeId = cabinetSeat.getFirearmTypeId() == null ? null:  Integer.parseInt(cabinetSeat.getFirearmTypeId());
                String ammoType = cabinetSeat.getAmmoType();
                Integer ammoTypeId = cabinetSeat.getAmmoTypeId();
                Integer ammoCount = cabinetSeat.getAmmoCount();
                Integer status = cabinetSeat.getStatus();
                Integer available = cabinetSeat.getAvailable();
                // ???????????????????????????
                String seatEntityId = (String) seat.get("@id");
                if (seatEntityId.equalsIgnoreCase(formSeatEntityId)) {
                    if (firearmType != null) {
                        seat.put("firearmType", firearmType);
                        seat.put("status", 1);
                    }
                    if (firearmTypeId != null) {
                        seat.put("firearmTypeId", firearmTypeId);
                    }
                    // ????????????
                    if (firearmNo != null) {
                        seat.put("firearmNo", firearmNo);
                    }
                    if (ammoType != null) {
                        seat.put("ammoType", ammoType);
                        seat.put("status", 1);
                    }
                    if (ammoTypeId != null) {
                        seat.put("ammoTypeId", ammoTypeId);
                    }
                    if (ammoCount != null) {
                        seat.put("ammoCount", ammoCount);
                    }
                    if (status != null) {
                        seat.put("status", status);
                    }
                    if (available != null) {
                        seat.put("available", available);
                    }
                }
            }
        }
    }
}
