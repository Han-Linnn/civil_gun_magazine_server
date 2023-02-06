package com.jingde.equipment.config;

import com.jingde.equipment.annotation.CurrentUser;
import com.jingde.equipment.model.User;
import com.jingde.equipment.core.exception.AuthException;
import com.jingde.equipment.app.user.service.UserService;
import com.jingde.equipment.app.user.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

/**
 * 增加方法注入，将含有 @CurrentUser 注解的参数注入当前登录用户
 */
public class CurrentUserArgResolver implements HandlerMethodArgumentResolver {
    private static Logger logger = LoggerFactory.getLogger("oceanover");
    @Resource
    UserService userService;

    @Override
    // 用于判定是否需要处理该参数分解，返回true为需要
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(User.class)
                && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    // 真正用于处理参数分解的方法
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        int userId = (int) nativeWebRequest.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        logger.info("userId: " + userId);
        UserInfoVO user = userService.cacheFindById(userId);
        if (user == null) {
            throw new AuthException("用户不存在，请重新登录");
        }
        if (user != null) {
            return user;
        }
        throw new MissingServletRequestParameterException("currentUser", User.class.toString());
    }
}
