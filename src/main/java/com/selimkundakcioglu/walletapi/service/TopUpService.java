package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.api.TopUpControllerApi;
import com.selimkundakcioglu.walletapi.configuration.jwt.JwtDecoder;
import com.selimkundakcioglu.walletapi.configuration.jwt.UserJwtClaims;
import com.selimkundakcioglu.walletapi.invokers.ApiException;
import com.selimkundakcioglu.walletapi.model.ResponseTransactionDto;
import com.selimkundakcioglu.walletapi.model.TopUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class TopUpService {

    private final JwtDecoder jwtDecoder;
    private final TopUpControllerApi topUpControllerApi;


    public ResponseTransactionDto topUp(HttpServletRequest httpServletRequest, TopUpRequest topUpRequest) throws ApiException {
        UserJwtClaims userJwtClaims = jwtDecoder.decodeJwt(httpServletRequest);
        topUpRequest.setUserId(userJwtClaims.getUserId().toString());
        return topUpControllerApi.topUp(topUpRequest);
    }
}
