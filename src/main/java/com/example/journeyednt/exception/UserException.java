package com.example.journeyednt.exception;

import com.example.journeyednt.result.ErrorCode;

public class UserException extends RuntimeException {
    private ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}