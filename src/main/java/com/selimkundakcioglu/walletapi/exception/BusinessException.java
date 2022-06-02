package com.selimkundakcioglu.walletapi.exception;

public class BusinessException extends BaseException {

    public BusinessException(String code, Object... args) {
        super(code, args);
    }
}
