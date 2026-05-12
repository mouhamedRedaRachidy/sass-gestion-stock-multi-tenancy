package com.rachidy.sassgestionstockapp.auth.services.impl;

import com.rachidy.sassgestionstockapp.auth.request.LoginRequest;
import com.rachidy.sassgestionstockapp.auth.response.LoginResponse;
import com.rachidy.sassgestionstockapp.auth.services.AuthentificationService;
import com.rachidy.sassgestionstockapp.entities.User;
import com.rachidy.sassgestionstockapp.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthentificationServiceImpl implements AuthentificationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public LoginResponse login(LoginRequest loginRequest){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        final User user=(User) authentication.getPrincipal();


        final String token=this.jwtTokenService.generateAccessToken(user.getId(),user.getTenantId(),user.getRole().name());
        final String tokenType="Bearer";

        return LoginResponse.builder()
                .token(token)
                .tokenType(tokenType)
                .build();
    }

}
