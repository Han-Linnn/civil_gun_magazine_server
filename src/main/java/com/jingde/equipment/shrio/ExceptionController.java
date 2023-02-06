package com.jingde.equipment.shrio;

import com.jingde.equipment.core.exception.AuthException;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultCode;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by oceanover on 2019-03-27.
 * @author
 */
@RestController
public class ExceptionController {
    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @GetMapping("/shiro/unauthorized")
    public Result handleShiroException() {
        Result result = new Result();
        String message = "权限不足";
        result.setState(ResultCode.FAIL).setMessage(message);
        return result;
    }

    @GetMapping("/error")
    public Result handleServerException(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        String message = "服务端错误";
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        Throwable cause = exception.getCause();
        if (cause instanceof AuthException) {
            message = "token无效，请检查令牌";
            result.setState(ResultCode.UNAUTHORIZED).setMessage(message);
        } else if (cause instanceof AuthenticationException) {
            message = cause.getMessage();
            result.setState(ResultCode.UNAUTHORIZED).setMessage(message);
        } else {
            result.setState(ResultCode.FAIL).setMessage(message);
        }
        logger.error(request.getRequestURI());
        logger.error(message);
        response.setStatus(200);
        return result;
    }
}
