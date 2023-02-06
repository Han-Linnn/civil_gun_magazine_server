package com.jingde.equipment.app.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.core.exception.ServiceException;
import com.jingde.equipment.app.system.dao.GroupMenuMapper;
import com.jingde.equipment.app.system.dao.GroupPermissionMapper;
import com.jingde.equipment.app.system.dao.GroupUserMapper;
import com.jingde.equipment.app.system.dao.PermissionMapper;
import com.jingde.equipment.app.police.dao.PoliceMapper;
import com.jingde.equipment.app.system.dto.GroupPermissionDTO;
import com.jingde.equipment.app.system.dto.GroupUserDTO;
import com.jingde.equipment.app.police.dto.PermissionDTO;
import com.jingde.equipment.model.GroupMenu;
import com.jingde.equipment.app.system.service.JurisdictionService;
import com.jingde.equipment.app.system.vo.PermissionVO;
import com.jingde.equipment.app.system.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理
 *
 * @author
 */
@Service
public class JurisdictionServiceImpl extends ServiceImpl<GroupMenuMapper, GroupMenu> implements JurisdictionService {

    @Resource
    private PoliceMapper policeMapper;

    @Resource
    private GroupMenuMapper groupMenuMapper;

    @Resource
    private GroupUserMapper groupUserMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private GroupPermissionMapper groupPermissionMapper;

    //-------------------资源----------------------------------
    @Override
    public List<PermissionVO> selectPermission() {
        return permissionMapper.selectPermission();
    }

    //---------------------------组与用户------------------------------------

    @Override
    public List<RoleVO> selectGroupUserById(Integer id) {
        if (null == id || 0 == id) {
            throw new ServiceException("组id为空");
        }
        //通过组id,获取角色用户
        return groupUserMapper.selectGroupUserById(id);
    }

    @Override
    public void deleteGroupUserById(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new ServiceException("请选择要删除的组员");
        }
        groupUserMapper.deleteGroupUserByIds(ids.split(","));
    }

    @Override
    public void addGroupUser(GroupUserDTO form) {
        if (null == form.getId() || 0 == form.getId()) {
            throw new ServiceException("组id为空");
        }
        if (null == form.getUserIds() || 0 == form.getUserIds().size()) {
            throw new ServiceException("用户userIds为空");
        }
        groupUserMapper.addGroupUser(form);
    }

    @Override
    public List<RoleVO> selectUnGroupUserByGroupId(PermissionDTO form) {
        if (StringUtils.isBlank(form.getId())) {
            throw new ServiceException("组id为空");
        }
        List<RoleVO> list = new ArrayList<>();
        //获取未添加入该组的人员列表
        list = groupUserMapper.selectUnGroupUserByGroupId(form);
        return list;
    }

    @Override
    public void deleteGroupUserBygroupId(String[] ids) {
        groupUserMapper.deleteGroupUserBygroupId(ids);
    }

    //--------------------------------组与资源-------------------------------------

    @Override
    public List<String> selectedMenuRouter(Integer id) {
        if (null == id || 0 == id) {
            throw new ServiceException("组id为空");
        }
        return groupMenuMapper.selectedMenuRouter(id);
    }

    @Override
    public void updateMenuRouter(PermissionDTO form) {
        if (StringUtils.isEmpty(form.getId())) {
            throw new ServiceException("组id为空");
        }
        if (null == form.getPermissionUrls() || 0 == form.getPermissionUrls().size()) {
            throw new ServiceException("资源集合permissionUrls为空");
        }
        //先删除该组原有的资源
        groupMenuMapper.deleteByGroupId(form.getId());
        //添加资源
        groupMenuMapper.insertByGroupId(form);
    }

    @Override
    public void deleteGroupPermissionBygroupId(String[] ids) {
        groupMenuMapper.deleteGroupPermissionBygroupId(ids);

    }

    @Override
    public List<String> selectedUserRouterByUserId(Integer id) {

        return groupMenuMapper.selectedUserRouterByUserId(id);
    }

    @Override
    public List<PermissionVO> selectGroupJurisdictionPage(String id) {
        if (StringUtils.isBlank(id)) {
            throw new ServiceException("id为空");
        }
        return groupPermissionMapper.selectGroupJurisdictionPage(id);
    }

    @Override
    public void updateGroupJurisdiction(GroupPermissionDTO form) {
        if (StringUtils.isBlank(form.getId())) {
            throw new ServiceException("组id为空");
        }
        //删除原先的权限
        groupPermissionMapper.deleteByGroupId(form.getId());
        if (null == form.getPermissionIds() || 0 == form.getPermissionIds().size()) {
        } else {
            //添加权限
            groupPermissionMapper.saveByGroupId(form);
        }


    }

    @Override
    public List<PermissionVO> selectAllJurisdictionPage() {
        return groupPermissionMapper.selectAllJurisdictionPage();
    }
}
