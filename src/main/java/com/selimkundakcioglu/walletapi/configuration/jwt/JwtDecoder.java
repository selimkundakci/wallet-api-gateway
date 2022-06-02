package com.selimkundakcioglu.walletapi.configuration.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtDecoder {

    private static final Logger logger = LoggerFactory.getLogger(JwtDecoder.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USER_ID_KEY = "userId";
    private static final String EMAIL_KEY = "email";
    private static final String PHONE_NUMBER_KEY = "phoneNumber";
    private static final String EXP_KEY = "exp";
    private static final int AUTHORIZATION_BEARER_LENGTH = 7;


    public UserJwtClaims decodeJwt(HttpServletRequest httpServletRequest) {
        UserJwtClaims userJwtClaims = new UserJwtClaims();
        try {
            extractClaimsFromAuthorizationToken(httpServletRequest.getHeader(AUTHORIZATION_HEADER), userJwtClaims);
        } catch (IOException e) {
            logger.error("error occurred while decoding jwt", e);
        }
        return userJwtClaims;
    }

    private void extractClaimsFromAuthorizationToken(String authorizationToken, UserJwtClaims userJwtClaims) throws IOException {
        String token = authorizationToken.substring(AUTHORIZATION_BEARER_LENGTH);
        Jwt jwt = JwtHelper.decode(token);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> claimsMap = objectMapper.readValue(jwt.getClaims(), new TypeReference<>() {
        });

        fillClaimsWithUserInfo(userJwtClaims, claimsMap);
        userJwtClaims.setToken(authorizationToken);
    }

    private void fillClaimsWithUserInfo(UserJwtClaims userJwtClaims, Map<String, Object> claims) {
        userJwtClaims.setUserId(Long.valueOf(claims.get(USER_ID_KEY).toString()));
        userJwtClaims.setEmail((String) claims.get(EMAIL_KEY));
        userJwtClaims.setPhoneNumber((String) claims.get(PHONE_NUMBER_KEY));
        Optional.ofNullable(claims.get(EXP_KEY)).ifPresent(o -> userJwtClaims.setExpireTime(Long.valueOf(o.toString())));
    }
}
