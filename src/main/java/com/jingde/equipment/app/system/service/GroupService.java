package com.jingde.equipment.app.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jingde.equipment.app.system.vo.RoleVO;
import com.jingde.equipment.model.Group;

import java.util.List;

/**
 * 权限管理
 * @author
 */
public interface GroupService extends IService<Group> {
    List<RoleVO> selectRoleBypage();
}
