package com.rachidy.sassgestionstockapp.controller;

import com.rachidy.sassgestionstockapp.common.PageResponse;
import com.rachidy.sassgestionstockapp.responses.TenantResponse;
import com.rachidy.sassgestionstockapp.services.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tenants")
public class TenantController {
    private final TenantService tenantService;

    @PostMapping("/aprove/{tenantId}")
    public ResponseEntity<Void>approveTenant(@PathVariable String tenantId){
        this.tenantService.approveTenant(tenantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/active/{tenantId}")
    public ResponseEntity<Void>activeTenant(@PathVariable String tenantId){
        this.tenantService.activeTenant(tenantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/deactivate/{tenantId}")
    public ResponseEntity<Void>deactivateActive(@PathVariable String tenantId){
        this.tenantService.deactivateTenant(tenantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/suspendTenant/{tenantId}")
    public ResponseEntity<Void>suspendTenantActive(@PathVariable String tenantId){
        this.tenantService.suspendTenant(tenantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<TenantResponse>> findAllTenant(
            @RequestParam(name = "page",defaultValue = "0")
            final int page,
            @RequestParam(name = "size", defaultValue = "0")
            final int size)
    {
        return ResponseEntity.ok(this.tenantService.findAll(page,size));
    }

}
