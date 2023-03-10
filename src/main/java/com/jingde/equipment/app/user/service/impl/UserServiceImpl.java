package com.jingde.equipment.app.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.record.dto.LableDTO;
import com.jingde.equipment.app.system.dao.PermissionMapper;
import com.jingde.equipment.app.user.dao.AuthorizeLogMapper;
import com.jingde.equipment.app.user.dao.UserMapper;
import com.jingde.equipment.app.user.dto.UserDTO;
import com.jingde.equipment.app.user.service.UserService;
import com.jingde.equipment.app.user.vo.UserInfoVO;
import com.jingde.equipment.app.user.vo.UserSonVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.model.User;
import com.jingde.equipment.util.Encrypt;
import com.jingde.equipment.util.RedisUtil;
import com.jingde.equipment.util.TokenUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.springframework.cache.annotation.Cacheable;

/**
 * Created by JingDe on 2019/02/20.
 *
 * @author
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private RedisUtil redisUtil;
    // ?????????????????????
    @Value("${app.env}")
    private String env;
    @Value("${app.hardware.prefixUrl}")
    private String hardwareUrl;
    @Resource
    private AuthorizeLogMapper authorizeLogMapper;

    @Override
    public Result login(String account, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("account", account);
        queryWrapper.allEq(queryMap);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return ResultGenerator.genNoContentResult("?????????????????????");
        }
        // ??????????????????
        if (user.getPassword().equals(Encrypt.encryptByMd5(password))) {
            // ??????redis?????????
            String key = String.format("access_token%d", user.getId());
            long time = 24 * 3600;
            if (env.equals("dev")) {
                // ?????????????????? 30???
                time = 30 * 24 * 3600;
            }
            // ???token??????
            String token;
            if (redisUtil.hasKey(key)) {
                token = (String) redisUtil.get(key);
                // ??????????????????
                boolean res = redisUtil.expire(key, time);
                if (!res) {
                    return ResultGenerator.genFailResult("?????????????????????");
                }
            } else {
                token = tokenUtil.generateToken(user);
                redisUtil.set(key, token, time);
            }
            return ResultGenerator.genSuccessResult(token);
        }
        return ResultGenerator.genNoContentResult("?????????????????????");
    }

    /**
     * ????????????????????????
     *
     * @param policeNo
     */
    @Override
    public Result quickLogin(String policeNo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("police_code", policeNo);
        queryWrapper.allEq(queryMap);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new ServiceException("??????????????????");
        }
        // ??????redis?????????
        String key = String.format("access_token%d", user.getId());
        long time = 24 * 3600;
        if (env.equals("dev")) {
            // ?????????????????? 30???
            time = 30 * 24 * 3600;
        }
        // ???token??????
        String token;
        if (redisUtil.hasKey(key)) {
            token = (String) redisUtil.get(key);
            // ??????????????????
            boolean res = redisUtil.expire(key, time);
            if (!res) {
                return ResultGenerator.genFailResult("?????????????????????");
            }
        } else {
            token = tokenUtil.generateToken(user);
            redisUtil.set(key, token, time);
        }
        return ResultGenerator.genSuccessResult(token);
    }

    /**
     * ????????????
     *
     * @param userId
     * @param password
     */
    @Override
    public void verify(Integer userId, String password) {
        UserInfoVO user = userMapper.findUserById(userId);
        if (user == null || !user.getPassword().equals(Encrypt.encryptByMd5(password))) {
            throw new ServiceException("??????????????????");
        }
    }

    @Override
    public UserInfoVO cacheFindById(Integer id) {
        // ????????????redis??????????????????
        if (env.equals("dev")) {
            // ??????????????????????????????
            logger.info(String.format("?????????????????????%s", env));
            UserInfoVO userInfo = userMapper.findUserById(id);
            if (null != userInfo) {
                userInfo.setPassword(null);
                //??????????????????,???????????????
                if (0 == userInfo.getIsSon()) {
                    UserSonVO sonVo = userMapper.selectSonAccount(userInfo.getPoliceCode());
                    userInfo.setUserSonvo(sonVo);
                } else {

                    userInfo.setUserSonvo(null);
                }
            }
            return userInfo;
        }
        String key = String.format("userInfo::%d", id);
        UserInfoVO value = (UserInfoVO) redisUtil.get(key);
        if (value == null) {
            UserInfoVO userInfo = userMapper.findUserById(id);
            if (null != userInfo) {
                userInfo.setPassword(null);
                //??????????????????,???????????????
                if (0 == userInfo.getIsSon()) {
                    UserSonVO sonVo = userMapper.selectSonAccount(userInfo.getPoliceCode());
                    userInfo.setUserSonvo(sonVo);
                } else {
                    UserSonVO sonVo = new UserSonVO();
                    userInfo.setUserSonvo(sonVo);
                }
            }
            logger.info("??????????????????");
            redisUtil.set(key, userInfo, 10 * 60);
            return userInfo;
        }
        logger.info("??????????????????");
        return value;
    }

    @Override
    public String selectPoliceNameByAccount(String account) {
        return userMapper.selectPoliceNameByAccount(account);
    }

    @Override
    public List<User> dutyUserSelect() {
        return userMapper.dutyUserSelect(1);
    }

    @Override
    public String getRoleById(Integer userId) {
        // TODO: get role
        return "user";
    }

    @Override
    public List getPermissionById(Integer userId) {
        List<Map> permissions = permissionMapper.selectUserPermissions(userId);
        List<String> permissionList = new ArrayList<>();
        for (Map permission : permissions) {
            String permissionName = (String) permission.get("permissionName");
            permissionList.add(permissionName);
        }
        return permissionList;
    }

    @Override
    public void deleteByPoliceIds(String ids) {
        userMapper.deleteByPoliceIds(ids.split(","));
    }

    @Override
    public void updateByPoliceId(UserDTO user) {
        userMapper.updateByPoliceId(user);
    }

    @Override
    public void updatePassword(User user, UserDTO form) {
        if (StringUtils.isBlank(form.getOldPassword())) {
            throw new ServiceException("???????????????");
        }
        if (StringUtils.isBlank(form.getNewPassword())) {
            throw new ServiceException("???????????????");
        }
        //????????????id,????????????
        User user1 = this.getById(user.getId());
        // ???????????????
        if (user1.getPassword().equals(Encrypt.encryptByMd5(form.getOldPassword()))) {
            form.setId(user1.getId() + "");
            form.setPassword(Encrypt.encryptByMd5(form.getNewPassword()));
            //????????????
            userMapper.updatePasswordById(form);
            //??????token??????
            String key = String.format("userInfo::%d", user.getId());
            String tokenKey = String.format("access_token%d", user.getId());
            redisUtil.del(key, tokenKey);
        } else {
            throw new ServiceException("???????????????");
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @Override
    public JSONObject faceInfo() {
        // ??????????????????
        String url = String.format("%s/api/face/log", hardwareUrl);
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseStr = response.body().string();
                Map<String, Object> resMap = (Map<String, Object>) JSONObject.parse(responseStr);
                Integer state = (Integer) resMap.get("state");
                if (state.equals(1000)) {
                    JSONArray data = (JSONArray) resMap.get("data");
                    if (data != null && data.size() > 0) {
                        return (JSONObject) data.get(0);
                    }
                }
            }
        } catch (IOException e) {
            throw new ServiceException("??????????????????????????????");
        }
        return null;
    }

    /**
     * ????????????(??????id,??????)
     *
     * @param userId
     * @param password
     * @return
     */
    @Override
    public void verifyUser(Integer userId, String password) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            String userPassword = user.getPassword();
            String encryptPassword = Encrypt.encryptByMd5(password);
            if (!userPassword.equals(encryptPassword)) {
                throw new ServiceException("?????????????????????");
            }
        } else {
            throw new ServiceException("?????????????????????");
        }
    }

    /**
     * ????????????(??????????????????)
     *
     * @param
     * @return
     */
    @Override
    public List<String> verifyUserByAccount(Map<String, String> map, String policeCode) {
        List<String> list = new ArrayList<>();
        if (null == policeCode) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String id = entry.getKey();
                String password = entry.getValue();
                if (null == id || null == password)
                    throw new ServiceException("????????????????????????");
                User user = userMapper.findUserById(Integer.parseInt(id));
                list.add(user.getId() + "-" + user.getPoliceName());
                if (user != null) {
                    String userPassword = user.getPassword();
                    String encryptPassword = Encrypt.encryptByMd5(password);
                    if (!userPassword.equals(encryptPassword)) {
                        throw new ServiceException("?????????????????????");
                    }
                } else {
                    throw new ServiceException("?????????????????????");
                }

            }
        } else {
            List<String> users = new ArrayList<>();
            users.add(policeCode);
            list = this.selectUserByAccount(users);
        }
        return list;
    }

    /**
     * ??????????????????(??????????????????,???????????????????????????????????????,???????????????)
     *
     * @param
     * @return
     */
    @Override
    public List<String> selectUserByCode(List<LableDTO> lableList) {
        if (null == lableList || 2 != lableList.size())
            throw new ServiceException("???????????????,???????????????????????????");
        Map<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(lableList.get(0).getPoliceCode())) {
            for (int i = 0; i < lableList.size(); i++) {
                LableDTO lableDTO = lableList.get(i);
                map.put(lableDTO.getKey(), lableDTO.getValue());
            }
            if (null == map || 2 != map.size())
                throw new ServiceException("???????????????,???????????????????????????");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String id = entry.getKey();
                String password = entry.getValue();
                if (null == id || null == password)
                    throw new ServiceException("????????????????????????");
                User user = userMapper.findUserById(Integer.parseInt(id));
                list.add(user.getId() + "-" + user.getPoliceName());
                if (user != null) {
                    String userPassword = user.getPassword();
                    String encryptPassword = Encrypt.encryptByMd5(password);
                    if (!userPassword.equals(encryptPassword)) {
                        throw new ServiceException("?????????????????????");
                    }
                } else {
                    throw new ServiceException("?????????????????????");
                }

            }
        } else {
            //???????????????????????????
            List<String> collect = lableList.stream().map(LableDTO::getPoliceCode).collect(Collectors.toList());
            list = this.selectUserByAccount(collect);
        }
        if (list.size() != 2) {
            throw new ServiceException("??????????????????,???????????????????????????");
        }
        return list;

    }

    public List<String> selectUserByAccount(List<String> list) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("account", list);
        List<User> users = userMapper.selectList(queryWrapper);
        List<String> resList = new ArrayList<>();
        for (User user : users) {
            resList.add(user.getId() + "-" + user.getPoliceName());
        }
        return resList;
    }

    /**
     * ?????????????????????
     *
     * @param form
     */
    @Override
    public void enableSubAccount(UserDTO form) {
        String password = "123456";
        if (StringUtils.isNotBlank(form.getPassword())) {
            password = form.getPassword();
        }
        //????????????????????????????????????
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("id", form.getId());
        queryMap.put("is_sub_account",1);
        queryWrapper.allEq(queryMap);
        //????????????????????????
        User user = userMapper.selectOne(queryWrapper);
        if (null == user) {
            throw new ServiceException("?????????????????????");
        }
        //???????????????
        if (null != form.getEnableSubAccount() && 0 == form.getEnableSubAccount()) {
            //?????????????????????
            form.setEnableSubAccount(0);
            if (null == form.getSubAccountId())
                throw new ServiceException("??????????????????????????????");
            //????????????????????????
            QueryWrapper<User> query = new QueryWrapper<>();
            Map<String, Object> map = new HashMap<>();
            map.put("id", form.getSubAccountId());
            map.put("is_sub_account",0);
            query.allEq(map);
            User assignUser = userMapper.selectOne(query);
            if (null == assignUser)
                throw new ServiceException("????????????????????????,???????????????");
            assignUser.setSubAccountId(form.getId());
            userMapper.updateById(assignUser);
        } else {
            //?????????????????????(?????????????????????)
            form.setEnableSubAccount(1);
        }
        //????????????????????????
        User oldAssignUser = userMapper.selectById(user.getSubAccountId());
        if (null != oldAssignUser) {
            oldAssignUser.setSubAccountId("");
            userMapper.updateById(oldAssignUser);
        }
        //?????????????????????
        userMapper.enableSubAccount(form.getEnableSubAccount(), Integer.parseInt(form.getId()), Encrypt.encryptByMd5(password),form.getSubAccountId());
    }

    @Override
    public List<User> listByCondition(Integer type) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("status", 1);
        queryMap.put("is_sub_account", 0);
        queryWrapper.allEq(queryMap);
        List<User> users = new ArrayList<>();
        users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            user.setPassword(null);
        }
        return users;
    }
}
