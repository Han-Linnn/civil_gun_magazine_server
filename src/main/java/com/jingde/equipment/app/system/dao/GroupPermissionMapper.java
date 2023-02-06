package com.jingde.equipment.app.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.app.system.dto.GroupPermissionDTO;
import com.jingde.equipment.model.GroupPermission;
import com.jingde.equipment.app.system.vo.PermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 */
public interface GroupPermissionMapper extends BaseMapper<GroupPermission> {

    List<PermissionVO> selectGroupJurisdictionPage(@Param("id") String id);

    void deleteByGroupId(@Param("id") String id);

    void saveByGroupId(@Param("condition") GroupPermissionDTO condition);

    List<PermissionVO> selectAllJurisdictionPage();
}
