package org.example.springbootdeveloper.config.error.exception;

import org.example.springbootdeveloper.config.error.ErrorCode;

// 내가 정의할 예외의 최상위 클래스
public class BusinessBaseException extends RuntimeException{

    private final ErrorCode errorCode;  // 내가 정의한 enum

    public BusinessBaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
