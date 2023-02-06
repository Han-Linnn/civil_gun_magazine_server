package com.jingde.equipment.app.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.Permission;
import com.jingde.equipment.app.system.vo.PermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    List<PermissionVO> selectPermission();

    /**
     * 获取用户的权限(包含授权的子账号的权限)
     *
     * @param userId
     * @return
     */
    List<Map> selectUserPermissions(@Param("userId") Integer userId);

    int selectItExistByUserIdAndPer(@Param("userId") Integer userId, @Param("permission") String permission);
}
