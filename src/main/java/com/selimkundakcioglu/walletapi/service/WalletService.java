package com.selimkundakcioglu.walletapi.service;

import com.selimkundakcioglu.walletapi.api.WalletControllerApi;
import com.selimkundakcioglu.walletapi.configuration.jwt.UserJwtClaims;
import com.selimkundakcioglu.walletapi.configuration.jwt.JwtDecoder;
import com.selimkundakcioglu.walletapi.invokers.ApiException;
import com.selimkundakcioglu.walletapi.model.CreateWalletAccountRequest;
import com.selimkundakcioglu.walletapi.model.CreateWalletRequest;
import com.selimkundakcioglu.walletapi.model.ResponseWalletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletControllerApi walletControllerApi;
    private final JwtDecoder jwtDecoder;

    public ResponseWalletDto queryWallet(HttpServletRequest httpServletRequest) throws ApiException {
        UserJwtClaims userJwtClaims = jwtDecoder.decodeJwt(httpServletRequest);
        return walletControllerApi.queryWallet(userJwtClaims.getUserId().toString());
    }

    public ResponseWalletDto createWallet(HttpServletRequest httpServletRequest, CreateWalletRequest createWalletRequest) throws ApiException {
        UserJwtClaims userJwtClaims = jwtDecoder.decodeJwt(httpServletRequest);
        createWalletRequest.setUserId(userJwtClaims.getUserId().toString());
        createWalletRequest.setPhoneNumber(userJwtClaims.getPhoneNumber());
        return walletControllerApi.createWallet(createWalletRequest);
    }

    public ResponseWalletDto createWalletAccount(HttpServletRequest httpServletRequest, CreateWalletAccountRequest createWalletAccountRequest) throws ApiException {
        UserJwtClaims userJwtClaims = jwtDecoder.decodeJwt(httpServletRequest);
        createWalletAccountRequest.setUserId(userJwtClaims.getUserId().toString());
        return walletControllerApi.createWalletAccount(createWalletAccountRequest);
    }
}
