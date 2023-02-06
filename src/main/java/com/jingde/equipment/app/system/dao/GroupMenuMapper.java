package com.jingde.equipment.app.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.police.dto.PermissionDTO;
import com.jingde.equipment.model.GroupMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface GroupMenuMapper extends BaseMapper<GroupMenu> {

    List<String> selectedMenuRouter(@Param("id") Integer id);

    void deleteByGroupId(@Param("id") String id);

    void insertByGroupId(@Param("condition") PermissionDTO condition);

    void deleteGroupPermissionBygroupId(@Param("ids")String[] ids);

    List<String> selectedUserRouterByUserId(@Param("id")Integer id);
}
