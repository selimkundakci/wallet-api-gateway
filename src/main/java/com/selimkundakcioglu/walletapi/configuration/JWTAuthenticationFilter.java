package com.selimkundakcioglu.walletapi.configuration;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selimkundakcioglu.walletapi.model.entity.WalletUser;
import com.selimkundakcioglu.walletapi.model.request.LoginRequest;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import com.selimkundakcioglu.walletapi.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String TOKEN_SIGNING_KEY = "getirsecret";
    private static final Long EXPIRATION_TIME = 3000000L;
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";
    private static final String LOGIN_URL = "/login";

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final ResponseFactory responseFactory;
    private final UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, ResponseFactory responseFactory, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.responseFactory = responseFactory;
        this.userService = userService;
        setFilterProcessesUrl(LOGIN_URL);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            final LoginRequest loginRequest = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            this.unsuccessfulAuthentication(req, res, null);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        WalletUser walletUser = userService.getWalletUserById(Long.valueOf(((User) auth.getPrincipal()).getUsername()));

        final String token = JWT.create()
                .withClaim("userId", walletUser.getId())
                .withClaim("email", walletUser.getEmail())
                .withClaim("phoneNumber", walletUser.getPhoneNumber())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(TOKEN_SIGNING_KEY.getBytes()));
        res.addHeader(AUTHORIZATION, BEARER + token);
        res.setStatus(HttpServletResponse.SC_OK);
        prepareResponse(res, prepareLoginResponse(token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        prepareResponse(response, responseFactory.response("Invalid email or password!", "Invalid email or password!"));
    }

    private Response<String> prepareLoginResponse(String authorizationToken) {
        return responseFactory.response(authorizationToken);
    }

    private void prepareResponse(HttpServletResponse res, Response<String> response) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        final PrintWriter writer = res.getWriter();
        writer.write(objectMapper.writeValueAsString(response));
        writer.flush();
        writer.close();
    }
}