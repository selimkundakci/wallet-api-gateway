package com.selimkundakcioglu.walletapi.model.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseFactory {

    public <T> Response<T> response() {
        return response(null);
    }

    public <T> Response<T> response(T data) {
        return response(data, null, null);
    }

    public <T> Response<T> response(String error, String message) {
        return response(null, error, message);
    }

    public <T> Response<T> response(T data, String error, String message) {
        Response<T> response = Response.<T>builder()
                .data(data)
                .error(error)
                .message(message)
                .build();
        log.info("Generated response: {}", response);
        return response;
    }
}