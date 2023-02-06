package com.jingde.equipment.app.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.system.vo.RoleVO;
import com.jingde.equipment.app.system.dto.GroupUserDTO;
import com.jingde.equipment.app.police.dto.PermissionDTO;
import com.jingde.equipment.model.GroupUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface GroupUserMapper extends BaseMapper<GroupUser> {

    List<RoleVO> selectGroupUserById(@Param("id") Integer id);

    void deleteGroupUserByIds(@Param("ids")String[] split);

    void addGroupUser(@Param("condition") GroupUserDTO condition);

    List<RoleVO> selectUnGroupUserByGroupId(@Param("condition") PermissionDTO condition);

    void deleteGroupUserBygroupId(@Param("ids") String[] ids);


}
