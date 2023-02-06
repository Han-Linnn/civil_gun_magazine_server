package com.jingde.equipment.config;

import com.jingde.equipment.annotation.LoginRequired;
import com.jingde.equipment.core.exception.AuthException;
import com.jingde.equipment.app.user.service.UserService;
import com.jingde.equipment.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Token 验证拦截器，配合自定义注解
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    @Autowired
    UserService userService;
    @Resource
    private TokenUtil tokenUtil;

    @Override
    // 处理请求前拦截
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info(httpServletRequest.getRequestURI());
        // 忽略不是注解在方法上的
        if (!(o instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) o;
        Method method = handlerMethod.getMethod();
        // 获取方法上注解
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 存在该注解
        if (methodAnnotation != null) {
            // 获取请求头token
            String token = httpServletRequest.getHeader("session-key");
            if (token == null) {
                throw new AuthException("请携带有效的令牌");
            }
            // 验证token，获取token加密内容
            Integer userId = tokenUtil.getUserId(token);
            httpServletRequest.setAttribute("userId", userId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
