package com.selimkundakcioglu.walletapi.configuration.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJwtClaims {

    private Long userId;
    private String email;
    private String phoneNumber;
    private String token;
    private Long expireTime;
}
