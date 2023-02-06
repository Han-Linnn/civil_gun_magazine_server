package com.jingde.equipment.app.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingde.equipment.app.system.vo.RoleVO;
import com.jingde.equipment.app.system.dao.GroupMapper;
import com.jingde.equipment.app.police.dao.PoliceMapper;
import com.jingde.equipment.model.Group;
import com.jingde.equipment.app.system.service.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理
 *
 * @author
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private PoliceMapper policeMapper;

    @Override
    public List<RoleVO> selectRoleBypage() {
        return groupMapper.selectAllRole();
    }

}
