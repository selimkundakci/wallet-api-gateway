package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.api.CheckoutControllerApi;
import com.selimkundakcioglu.walletapi.configuration.jwt.JwtDecoder;
import com.selimkundakcioglu.walletapi.configuration.jwt.UserJwtClaims;
import com.selimkundakcioglu.walletapi.invokers.ApiException;
import com.selimkundakcioglu.walletapi.model.CheckoutRequest;
import com.selimkundakcioglu.walletapi.model.ResponseTransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CheckoutControllerApi checkoutControllerApi;
    private final JwtDecoder jwtDecoder;

    public ResponseTransactionDto checkout(HttpServletRequest httpServletRequest, CheckoutRequest checkoutRequest) throws ApiException {
        UserJwtClaims userJwtClaims = jwtDecoder.decodeJwt(httpServletRequest);
        checkoutRequest.setUserId(userJwtClaims.getUserId().toString());
        return checkoutControllerApi.checkout(checkoutRequest);
    }
}
