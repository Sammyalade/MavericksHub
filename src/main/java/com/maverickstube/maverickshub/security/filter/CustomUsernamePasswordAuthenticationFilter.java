package com.maverickstube.maverickshub.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maverickstube.maverickshub.dtos.request.LoginRequest;
import com.maverickstube.maverickshub.dtos.response.BaseResponse;
import com.maverickstube.maverickshub.dtos.response.LoginResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            return authenticate(retrieveAuthCredentialsFrom(request));
        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        Authentication authentication = new
                UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticationResult =  authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        return authenticationResult;
    }

    private LoginRequest retrieveAuthCredentialsFrom(HttpServletRequest request) throws IOException {
        InputStream requestBodyStream = request.getInputStream();
        return mapper.readValue(requestBodyStream, LoginRequest.class);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(generateAccessToken(authResult));
        loginResponse.setMessage("Successful Authentication");
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setStatus(true);

        response.getOutputStream().write(mapper.writeValueAsBytes(baseResponse));
        response.flushBuffer();
        chain.doFilter(request, response);
    }

    private static String generateAccessToken(Authentication authResult) {
        return JWT.create()
                .withIssuer("MavericksHub")
                .withArrayClaim("roles",
                        getClaimsFrom(authResult.getAuthorities()))
                .withExpiresAt(Instant.now()
                        .plusSeconds(24*60*60))
                .sign(Algorithm.HMAC512("secret"));
    }

    private static String[] getClaimsFrom(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException exception) throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(exception.getMessage());
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(false);
        baseResponse.setCode(HttpStatus.UNAUTHORIZED.value());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().write(mapper.writeValueAsBytes(baseResponse));
        response.flushBuffer();
    }
}
