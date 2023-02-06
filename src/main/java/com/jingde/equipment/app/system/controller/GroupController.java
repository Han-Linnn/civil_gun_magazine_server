package com.jingde.equipment.app.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.system.dto.GroupDTO;
import com.jingde.equipment.app.system.service.GroupService;
import com.jingde.equipment.app.system.service.JurisdictionService;
import com.jingde.equipment.app.system.vo.RoleVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.Group;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Goldrepo on 2019/2/28
 * @author
 */
@RestController
@RequestMapping("/api/permission/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    @Resource
    private JurisdictionService jurisdictionService;

    /**
     * 增加权限组
     * /api/permission/group/info
     * @param
     * @param
     * @return
     */
    @PostMapping("/info")
    @LoginRequired
    public Result groupAdd(@RequestBody GroupDTO form) {
        Group group = new Group();
        if (StringUtils.isNotBlank(form.getGroupName())) {
            group.setGroupName(form.getGroupName());
        }else {
            return ResultGenerator.genFailResult("groupName为空");
        }
        groupService.save(group);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 修改权限组
     * /api/permission/group/info
     * @param
     * @param
     * @return
     */
    @PutMapping("/info")
    @LoginRequired
    public Result groupUpdate(@RequestBody GroupDTO form) {

        Group group = new Group();
        if (StringUtils.isNotBlank(form.getId())) {
            group.setId(Integer.valueOf(form.getId()));
        }else {
            return ResultGenerator.genFailResult("id为空");
        }
        if (StringUtils.isNotBlank(form.getGroupName())) {
            group.setGroupName(form.getGroupName());
        }else {
            return ResultGenerator.genFailResult("groupName为空");
        }
        groupService.saveOrUpdate(group);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * /api/permission/group/info
     * @param ids
     * @return
     */
    @DeleteMapping("/info")
    @LoginRequired
    public Result groupDelete(String ids) {
        if (StringUtils.isBlank(ids)) {
            return ResultGenerator.genFailResult("请选择要删除的组");
        }
        //删除组
        groupService.removeById(ids);
        //删除该组下绑定的用户
        jurisdictionService.deleteGroupUserBygroupId(ids.split(","));
        //删除该组下选择的资源
        jurisdictionService.deleteGroupPermissionBygroupId(ids.split(","));
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 获取权限组列表
     * /permission/group/page
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/page")
    @LoginRequired
    public Result groupPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<RoleVO> list = groupService.selectRoleBypage();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
