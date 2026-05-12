package com.rachidy.sassgestionstockapp.services;


import com.rachidy.sassgestionstockapp.common.PageResponse;
import com.rachidy.sassgestionstockapp.requests.RegisterTenantRequest;
import com.rachidy.sassgestionstockapp.responses.TenantResponse;

public interface TenantService {

    void registerTenant(final RegisterTenantRequest request);
    void approveTenant(final String tenantId);
    void activeTenant(final String tenantId);
    void deactivateTenant(final String tenantId);
    void suspendTenant(final String tenantId);
    PageResponse<TenantResponse> findAll(final int page,final int size);
}