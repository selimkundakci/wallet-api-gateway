package com.selimkundakcioglu.walletapi.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final String code;
    private final Object[] args;

    BaseException(String code, Object... args) {
        this.code = code;
        this.args = args;
    }
}
