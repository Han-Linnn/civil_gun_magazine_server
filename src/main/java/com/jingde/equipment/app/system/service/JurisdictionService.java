package com.jingde.equipment.app.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.system.dto.GroupPermissionDTO;
import com.jingde.equipment.app.system.dto.GroupUserDTO;
import com.jingde.equipment.app.police.dto.PermissionDTO;
import com.jingde.equipment.model.GroupMenu;
import com.jingde.equipment.app.system.vo.PermissionVO;
import com.jingde.equipment.app.system.vo.RoleVO;

import java.util.List;

/**
 * 权限管理
 * @author
 */
public interface JurisdictionService extends IService<GroupMenu> {

    List<PermissionVO> selectPermission();

    List<RoleVO> selectGroupUserById(Integer id);

    void deleteGroupUserById(String ids);

    void addGroupUser(GroupUserDTO form);

    List<String> selectedMenuRouter(Integer id);

    void updateMenuRouter(PermissionDTO form);

    List<RoleVO> selectUnGroupUserByGroupId(PermissionDTO form);

    void deleteGroupUserBygroupId(String[] split);

    void deleteGroupPermissionBygroupId(String[] split);

    List<String> selectedUserRouterByUserId(Integer id);


    List<PermissionVO> selectGroupJurisdictionPage(String id);

    void updateGroupJurisdiction(GroupPermissionDTO form);

    List<PermissionVO> selectAllJurisdictionPage();
}
