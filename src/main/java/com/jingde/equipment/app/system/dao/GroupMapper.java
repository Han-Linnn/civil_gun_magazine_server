package com.jingde.equipment.app.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingde.equipment.model.Group;
import com.jingde.equipment.app.system.vo.RoleVO;

import java.util.List;

/**
 * @author
 */
public interface GroupMapper extends BaseMapper<Group> {

    List<RoleVO> selectAllRole();

}
