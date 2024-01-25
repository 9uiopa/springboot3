package org.example.springbootdeveloper.config.error;

// ErrorAttributes 대체용 , 에러 메시지의 객체다.
public class ErrorResponse {
    private String message;
    private String code;

    // 이하 전부 생성자

    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.code = code.getCode();
    }

    public ErrorResponse(final ErrorCode code, final String message) {
        this.message = message;
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final String message) {
        return new ErrorResponse(code, message);
    }
}
