package com.jingde.equipment.app.user.controller;

import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultGenerator;
import com.jingde.equipment.app.user.dto.UserDTO;
import com.jingde.equipment.model.User;
import com.jingde.equipment.app.user.service.UserService;
import com.jingde.equipment.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by JingDe on 2019/02/20.
 *
 * @author
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger("oceanover");
    @Resource
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String account = user.getAccount();
        String password = user.getPassword();
        logger.info(account);
        return userService.login(account, password);
    }

    /**
     * 警员编号快速登录
     *
     * @param params
     * @return
     */
    @PostMapping("/quickLogin")
    public Result quickLogin(@RequestBody Map<String, String> params) {
        String policeNo = params.get("policeNo");
        return userService.quickLogin(policeNo);
    }

    /**
     * 验证用户
     *
     * @param request
     * @return
     */
    @LoginRequired
    @PostMapping("/verify")
    public Result verify(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Integer userId = (Integer) request.getAttribute("userId");
        String password = params.get("password");
        userService.verify(userId, password);
        return ResultGenerator.genSuccessResult();
    }

    @LoginRequired
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        String key = String.format("access_token%d", userId);
        redisUtil.del(key);
        return ResultGenerator.genSuccessResult();
    }

    @LoginRequired
    @GetMapping("/info")
    public Result userInfo(@CurrentUser User sUser) {
        return ResultGenerator.genSuccessResult(sUser);
    }

    @GetMapping("/duty/select")
    public Result dutyUserSelect() {
        List<User> list = userService.dutyUserSelect();
        return ResultGenerator.genSuccessResult(list);
    }

    @LoginRequired
    @PostMapping("/updatePassword")
    public Result updatePassword(@CurrentUser User user, @RequestBody UserDTO form) {

        userService.updatePassword(user, form);
        return ResultGenerator.genSuccessResult();
    }

    @LoginRequired
    @GetMapping("/permissions")
    public Result userPermissions(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        List permissionList = userService.getPermissionById(userId);
        return ResultGenerator.genSuccessResult(permissionList);
    }

    /**
     * 获取最新识别的人脸信息
     *
     * @return
     */
    @GetMapping("/face")
    public Result faceInfo() {
        Map result = userService.faceInfo();
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 修改授权的账号(将子账号授权给指定用户)
     * @param user
     * @return
     */
    @LoginRequired
    @PostMapping("/enableSubAccount")
    public Result enableSubAccount(@RequestBody UserDTO user){
        userService.enableSubAccount(user);
        return ResultGenerator.genSuccessResult();
    }

}
