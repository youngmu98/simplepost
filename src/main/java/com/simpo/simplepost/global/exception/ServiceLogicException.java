package com.simpo.simplepost.global.exception;

import lombok.Getter;

public class ServiceLogicException extends RuntimeException {
    
    @Getter
    private final ExceptionCode exceptionCode;

    public ServiceLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}
