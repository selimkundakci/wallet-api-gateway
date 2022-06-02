package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.invokers.ApiException;
import com.selimkundakcioglu.walletapi.model.CreateWalletAccountRequest;
import com.selimkundakcioglu.walletapi.model.CreateWalletRequest;
import com.selimkundakcioglu.walletapi.model.ResponseWalletDto;
import com.selimkundakcioglu.walletapi.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    private ResponseWalletDto queryWallet(HttpServletRequest httpServletRequest) throws ApiException {
        return walletService.queryWallet(httpServletRequest);
    }

    @PostMapping
    private ResponseWalletDto createWallet(HttpServletRequest httpServletRequest, @RequestBody CreateWalletRequest createWalletRequest) throws ApiException {
        return walletService.createWallet(httpServletRequest, createWalletRequest);
    }

    @PostMapping("/account")
    private ResponseWalletDto createWalletAccount(HttpServletRequest httpServletRequest, @RequestBody CreateWalletAccountRequest createWalletAccountRequest) throws ApiException {
        return walletService.createWalletAccount(httpServletRequest, createWalletAccountRequest);
    }
}
