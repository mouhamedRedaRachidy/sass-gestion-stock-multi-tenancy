package com.rachidy.sassgestionstockapp.auth.controller;

import com.rachidy.sassgestionstockapp.auth.request.LoginRequest;
import com.rachidy.sassgestionstockapp.auth.response.LoginResponse;
import com.rachidy.sassgestionstockapp.auth.services.AuthentificationService;
import com.rachidy.sassgestionstockapp.requests.RegisterTenantRequest;
import com.rachidy.sassgestionstockapp.services.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthentificationService authentificationService;
    private final TenantService tenantService;

    @PostMapping("/auth/v1/login")
    public ResponseEntity<LoginResponse>login(@RequestBody @Valid LoginRequest loginRequest){
        authentificationService.login(loginRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("auth/v1/registre")
    public ResponseEntity<Void>register(@RequestBody @Valid RegisterTenantRequest request){
        this.tenantService.registerTenant(request);
        return ResponseEntity.ok().build();
    }
}
