package com.jingde.equipment.core;

import com.github.pagehelper.PageInfo;
import com.jingde.equipment.util.PageUtil;

import java.util.Map;

/**
 * 响应结果生成工具
 *
 * @author
 */
public class ResultGenerator {
    private static final String SUCCESS_MESSAGE = "请求成功";

    public static Result genSuccessResult() {
        return new Result()
                .setState(ResultCode.SUCCESS)
                .setMessage(SUCCESS_MESSAGE);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        if (data == null) {
            return new Result()
                    .setState(ResultCode.SUCCESS)
                    .setMessage(SUCCESS_MESSAGE);
        }
        // 统一处理分页结果
        if (data.getClass().equals(PageInfo.class)) {
            Map result = PageUtil.formatPageResult((PageInfo) data);
            return new Result()
                    .setState(ResultCode.SUCCESS)
                    .setMessage(SUCCESS_MESSAGE)
                    .setData(result);
        }
        return new Result()
                .setState(ResultCode.SUCCESS)
                .setMessage(SUCCESS_MESSAGE)
                .setData(data);
    }

    public static <T> Result<T> genNoContentResult(String message) {
        return new Result()
                .setState(ResultCode.NO_CONTENT)
                .setMessage(message);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setState(ResultCode.FAIL)
                .setMessage(message);
    }
}
