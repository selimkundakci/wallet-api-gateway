package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.invokers.ApiException;
import com.selimkundakcioglu.walletapi.model.CheckoutRequest;
import com.selimkundakcioglu.walletapi.model.ResponseTransactionDto;
import com.selimkundakcioglu.walletapi.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseTransactionDto checkout(HttpServletRequest httpServletRequest, @RequestBody CheckoutRequest checkoutRequest) throws ApiException {
        return checkoutService.checkout(httpServletRequest, checkoutRequest);
    }
}
