package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.model.request.RegisterRequest;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import com.selimkundakcioglu.walletapi.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    private final ResponseFactory responseFactory;

    @PostMapping("/register")
    public Response<String> register(@RequestBody RegisterRequest registerRequest) {
        registerService.register(registerRequest);
        return responseFactory.response();
    }
}
