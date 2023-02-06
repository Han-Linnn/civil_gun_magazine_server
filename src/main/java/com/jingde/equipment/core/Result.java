package com.jingde.equipment.core;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 * @author
 */
public class Result<T> {
    private int state;
    private String message;
    private T data;

    // 自定义的状态码
    public Result setState(ResultCode resultCode) {
        this.state = resultCode.state();
        return this;
    }

    public int getState() {
        return state;
    }

    // 提示消息
    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    // 返回的内容
    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
