package com.jingde.equipment.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 * @author
 */
public enum ResultCode {
//    // 请求成功
//    SUCCESS(200),
//    // 请求成功处理， 但是返回的响应没 有数据
//    NO_CONTENT(204),
//    // 请求无效或不一致
//    FAIL(400),
//    // 请求未包含身份验证信息， 或者提 供的凭据无效
//    UNAUTHORIZED(401),
//    // 接口不存在
//    NOT_FOUND(404),
//    // 内部服务器错误
//    INTERNAL_SERVER_ERROR(500);

    // 请求成功
    SUCCESS(0),
    // 请求成功处理， 但是返回的响应没有数据
    NO_CONTENT(2),
    // 请求无效或不一致
//    FAIL(400),
    FAIL(-1),
    // 请求未包含身份验证信息， 或者提 供的凭据无效
    UNAUTHORIZED(4),
    // 拒绝访问操作资源（权限）
    FORBIDDEN(3),
    // 接口不存在
    NOT_FOUND(404),
    // 内部服务器错误
    INTERNAL_SERVER_ERROR(-1);
    private final int state;

    ResultCode(int state) {
        this.state = state;
    }

    public int state() {
        return state;
    }
}
