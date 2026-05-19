package com.waal.common.exception;

import lombok.Getter;

@Getter
public class WaalException extends RuntimeException {

    private final ErrorCode errorCode;

    public WaalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public WaalException(ErrorCode errorCode, String detail) {
        super(detail);
        this.errorCode = errorCode;
    }
}
