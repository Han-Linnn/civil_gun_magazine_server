package com.jingde.equipment.app.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.app.police.dto.PermissionDTO;
import com.jingde.equipment.app.system.dto.GroupPermissionDTO;
import com.jingde.equipment.app.system.dto.GroupUserDTO;
import com.jingde.equipment.app.system.service.JurisdictionService;
import com.jingde.equipment.app.system.vo.PermissionVO;
import com.jingde.equipment.app.system.vo.RoleVO;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.model.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理
 * @author
 */

@RestController
@RequestMapping("/api/permission")
public class JurisdictionController {

    @Resource
    private JurisdictionService jurisdictionService;

    /**
     * 获取所有的资源列表
     * /api/permission/select
     * @return
     */
    @GetMapping("/select")
    @LoginRequired
    public Result selectPermission() {
        List<PermissionVO> list = jurisdictionService.selectPermission();
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     *根据组id获取所有组员
     * /api/permission/user/info
     * @param
     * @param
     * @return
     */
    @GetMapping("/user/info")
    @LoginRequired
    public Result groupUserPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, Integer id) {
        PageHelper.startPage(page, size);
        List<RoleVO> list = jurisdictionService.selectGroupUserById(id);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    /**
     *删除权限组中的用户
     * /api/permission/user/info
     * @param
     * @param
     * @return
     */
    @DeleteMapping("/user/info")
    @LoginRequired
    public Result deleteRoleUser(String ids) {
        jurisdictionService.deleteGroupUserById(ids);
        return ResultGenerator.genSuccessResult();
    }

    /**
     *增加权限组中的用户
     * /api/permission/user/info
     * @param
     * @param
     * @return
     */
    @PostMapping("/user/info")
    @LoginRequired
    public Result addRoleUser(@RequestBody GroupUserDTO form) {
        jurisdictionService.addGroupUser(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     *根据组id获取未添加入该组的人员列表
     * /api/permission/user/unAdd
     * @param
     * @param
     * @return
     */
    @GetMapping("/user/unAdd")
    @LoginRequired
    public Result unAdduser(PermissionDTO form) {
        List<RoleVO> list = jurisdictionService.selectUnGroupUserByGroupId(form);
        return ResultGenerator.genSuccessResult(list);
    }
    //--------------组与资源-------------------------------------------
    /**
     * 根据组id获取当前已经勾选的路由
     * /api/permission/router/info
     * @param
     * @return
     */
    @GetMapping("/router/info")
    @LoginRequired
    public Result selectedMenuRouter(Integer id){
        List<String> list = jurisdictionService.selectedMenuRouter(id);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 根据组id修改路由
     *  /api/permission/router/info
     * @param
     * @return
     */
    @PutMapping("/router/info")
    @LoginRequired
    public Result updateMenuRouter( @RequestBody PermissionDTO form){
        jurisdictionService.updateMenuRouter(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据当前用户获取当前用户所在的组已经勾选的路由
     * /api/permission/user/router
     * @param
     * @return
     */
    @GetMapping("/user/router")
    @LoginRequired
    public Result selectedUserRouter(@CurrentUser User user){
        List<String> list = jurisdictionService.selectedUserRouterByUserId(user.getId());
        return ResultGenerator.genSuccessResult(list);
    }

    //组与权限的操作

    /**
     * 获取当前用户组已经勾选的权限
     * /api/permission/jurisdiction/info
     */
    @GetMapping("/jurisdiction/info")
    @LoginRequired
    public Result selectGroupJurisdictionPage(String id){
        List<PermissionVO> list = jurisdictionService.selectGroupJurisdictionPage(id);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 修改用户组权限
     * /api/permission/jurisdiction/info
     */
    @PutMapping("/jurisdiction/info")
    @LoginRequired
    public Result updateGroupJurisdiction(@RequestBody GroupPermissionDTO form){
        jurisdictionService.updateGroupJurisdiction(form);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 所有的权限列表
     * /api/permission/jurisdiction/select
     */
    @GetMapping("/jurisdiction/select")
    @LoginRequired
    public Result selectAllJurisdictionPage(){
        List<PermissionVO> list = jurisdictionService.selectAllJurisdictionPage();
        return ResultGenerator.genSuccessResult(list);
    }


}
