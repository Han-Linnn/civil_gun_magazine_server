package com.jingde.equipment.core.exception;

/**
 * token 验证的异常
 * @author
 */
public class AuthException extends RuntimeException {
    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
