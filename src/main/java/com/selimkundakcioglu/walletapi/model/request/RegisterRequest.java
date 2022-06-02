package com.selimkundakcioglu.walletapi.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String phoneNumber;
    private String email;
    private String password;
}
