package com.jingde.equipment.config;

import com.jingde.equipment.core.exception.AuthException;
import com.jingde.equipment.core.Result;
import com.jingde.equipment.core.ResultCode;
import com.jingde.equipment.core.exception.ServiceException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

// 全局异常处理
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public Result ex(MethodArgumentNotValidException exception, HttpServletResponse response){
		Result result = new Result();
		BindingResult bindingResult = exception.getBindingResult();
		List<String> errors = getErrors(bindingResult);
		result.setState(ResultCode.FAIL).setMessage(String.join(",",errors));
		return result;
	}

	private List<String> getErrors(BindingResult result){
		List<String> message = new ArrayList<>();
		List<FieldError> list = result.getFieldErrors();
		for(FieldError error : list){
			message.add(error.getDefaultMessage());
		}
		return message;
	}

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(request.getRequestURI());
        Result result = new Result();
        if (e instanceof ServiceException) {
            // 业务失败的异常
            logger.error("", e);
            result.setState(ResultCode.FAIL).setMessage(e.getMessage());
        } else if (e instanceof AuthException) {
            // token验证错误
            result.setState(ResultCode.UNAUTHORIZED).setMessage(e.getMessage());
        } else if (e instanceof FileUploadException) {
            logger.error("", e);
            result.setState(ResultCode.FAIL).setMessage(e.getMessage());
        } else if (e instanceof NoHandlerFoundException) {
            logger.error("", e);
            result.setState(ResultCode.NOT_FOUND).setMessage("接口不存在");
        } else if (e instanceof UnauthorizedException) {
            logger.warn("", e);
            result.setState(ResultCode.FAIL).setMessage("权限不足");
        } else if (e instanceof ServletException) {
            logger.error("", e);
            result.setState(ResultCode.FAIL).setMessage(e.getMessage());
        } else {
            logger.error("", e);
            result.setState(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口内部错误");
        }
        response.setStatus(200);
        return result;
    }
}
