package com.selimkundakcioglu.walletapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
public class WalletApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletApiGatewayApplication.class, args);
    }

}
