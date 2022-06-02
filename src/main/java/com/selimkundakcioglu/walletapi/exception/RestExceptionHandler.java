package com.selimkundakcioglu.walletapi.exception;

import com.selimkundakcioglu.walletapi.invokers.ApiException;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestExceptionHandler {

    private final ResponseFactory responseFactory;
    private final MessageSource messageSource;

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public Response<String> handleApiExceptions(ApiException e) {
        return handleApiException(e, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public <T> Response<T> handleBusinessException(BusinessException e) {
        return handleAppException(e, e.getCode(), e.getArgs());
    }

    @ExceptionHandler(Exception.class)
    public <T> Response<T> handleException(Exception e, HttpServletResponse httpServletResponse) {
        log.error("{} is caught.", e.getClass().getName(), e);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        httpServletResponse.setStatus(httpStatus.value());
        return responseFactory.response(String.valueOf(httpStatus.value()), httpStatus.getReasonPhrase());
    }

    private <T> Response<T> handleAppException(BaseException e, String error, Object... args) {
        log.error("{} is caught.", e.getClass().getName(), e);
        String message = messageSource.getMessage(error, args, Locale.getDefault());
        return responseFactory.response(error, message);
    }

    private Response<String> handleApiException(ApiException e, String error, Object... args) {
        log.error("{} is caught.", e.getClass().getName(), e);
        return responseFactory.response(e.getResponseBody());
    }
}