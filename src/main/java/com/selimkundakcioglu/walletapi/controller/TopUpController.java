package com.selimkundakcioglu.walletapi.controller;

import com.selimkundakcioglu.walletapi.invokers.ApiException;
import com.selimkundakcioglu.walletapi.model.ResponseTransactionDto;
import com.selimkundakcioglu.walletapi.model.TopUpRequest;
import com.selimkundakcioglu.walletapi.service.TopUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/topup")
@RequiredArgsConstructor
public class TopUpController {

    private final TopUpService topUpService;

    @PostMapping()
    @ResponseStatus(CREATED)
    public ResponseTransactionDto topUp(HttpServletRequest httpServletRequest, @RequestBody TopUpRequest topUpRequest) throws ApiException {
        return topUpService.topUp(httpServletRequest, topUpRequest);
    }
}
