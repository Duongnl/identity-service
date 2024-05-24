package com.devteria.idetityservice.exception;

public class AppException extends  RuntimeException  {

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // goi contructer cua lop cha, lop cha truyen vao thong diep, appException.getMassage()
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
