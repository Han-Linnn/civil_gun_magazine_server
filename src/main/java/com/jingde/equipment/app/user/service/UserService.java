package com.jingde.equipment.app.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.record.dto.LableDTO;
import com.jingde.equipment.model.User;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.app.user.dto.UserDTO;
import com.jingde.equipment.app.user.vo.UserInfoVO;

import java.util.List;
import java.util.Map;

/**
 * Created by JingDe on 2019/02/20.
 *
 * @author
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    Result login(String account, String password);

    /**
     * 警员编号快速登录
     *
     * @param policeNo
     */
    Result quickLogin(String policeNo);

    /**
     * 验证用户
     *
     * @param userId
     * @param password
     */
    void verify(Integer userId, String password);

    UserInfoVO cacheFindById(Integer id);

    String selectPoliceNameByAccount(String account);

    List<User> dutyUserSelect();

    /**
     * 获取用户角色
     */
    String getRoleById(Integer userId);

    /**
     * 获取用户权限
     */
    List getPermissionById(Integer userId);

    void deleteByPoliceIds(String ids);

    void updateByPoliceId(UserDTO user);

    void updatePassword(User user, UserDTO form);

    /**
     * 获取最新识别的人脸信息
     *
     * @return
     */
    JSONObject faceInfo();

    void verifyUser(Integer userId, String password);

    List<String> verifyUserByAccount( Map<String, String> map ,String policeCode);

    List<String> selectUserByCode(List<LableDTO> list);

    /**
     * 修改子账号状态
     * @param user
     * @param 
     */
    void enableSubAccount(UserDTO user);

    List<User> listByCondition(Integer type);
}
