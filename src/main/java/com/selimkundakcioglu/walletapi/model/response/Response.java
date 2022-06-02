package com.selimkundakcioglu.walletapi.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Response<T> {

    private T data;
    private String error;
    private String message;
}