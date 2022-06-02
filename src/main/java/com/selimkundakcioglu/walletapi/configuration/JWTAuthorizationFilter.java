package com.selimkundakcioglu.walletapi.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selimkundakcioglu.walletapi.model.response.Response;
import com.selimkundakcioglu.walletapi.model.response.ResponseFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_SIGNING_KEY = "getirsecret";
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    private final ObjectMapper objectMapper;
    private final ResponseFactory responseFactory;

    public JWTAuthorizationFilter(AuthenticationManager authManager, ObjectMapper mapper, ResponseFactory responseFactory) {
        super(authManager);
        this.objectMapper = mapper;
        this.responseFactory = responseFactory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader(AUTHORIZATION);

        if (token == null || !token.startsWith(BEARER)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication;
        try {
            authentication = getAuthentication(token);
        } catch (Exception exception) {
            SecurityContextHolder.clearContext();
            prepareResponse(res, responseFactory.response(exception.getMessage(), exception.getMessage()));
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        final String email = JWT.require(Algorithm.HMAC512(TOKEN_SIGNING_KEY.getBytes()))
                .build()
                .verify(token.replace(BEARER, ""))
                .getClaim("email")
                .asString();
        if (email != null) {
            return new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
        }
        throw new BadCredentialsException("Email is not present in token!");
    }

    private void prepareResponse(HttpServletResponse res, Response<Object> response) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        final PrintWriter writer = res.getWriter();
        writer.write(objectMapper.writeValueAsString(response));
        writer.flush();
        writer.close();
    }
}
