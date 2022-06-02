package com.selimkundakcioglu.walletapi.configuration;

import com.selimkundakcioglu.walletapi.api.CheckoutControllerApi;
import com.selimkundakcioglu.walletapi.api.TopUpControllerApi;
import com.selimkundakcioglu.walletapi.api.WalletControllerApi;
import com.selimkundakcioglu.walletapi.invokers.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WalletApiConfiguration {

    @Value("${wallet-api.base-url}")
    private String walletApiBaseUrl;

    @Value("${wallet-api.client-timeout-duration}")
    private int walletApiClientTimeOutDuration;

    @Bean
    public WalletControllerApi walletControllerApi() {
        return new WalletControllerApi(walletApiClient());
    }

    @Bean
    public CheckoutControllerApi checkoutApi() {
        return new CheckoutControllerApi(walletApiClient());
    }

    @Bean
    public TopUpControllerApi topUpApi() {
        return new TopUpControllerApi(walletApiClient());
    }

    @Bean
    public ApiClient walletApiClient() {
        var walletApiClient = new ApiClient();
        walletApiClient.setBasePath(walletApiBaseUrl);
        walletApiClient.setConnectTimeout(walletApiClientTimeOutDuration);
        walletApiClient.setWriteTimeout(walletApiClientTimeOutDuration);
        walletApiClient.setReadTimeout(walletApiClientTimeOutDuration);
        walletApiClient.setDebugging(true);
        return walletApiClient;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
