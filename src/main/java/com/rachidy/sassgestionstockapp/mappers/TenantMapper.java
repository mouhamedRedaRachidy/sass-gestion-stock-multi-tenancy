package com.rachidy.sassgestionstockapp.mappers;

import com.rachidy.sassgestionstockapp.entities.Tenant;
import com.rachidy.sassgestionstockapp.entities.TenantStatus;
import com.rachidy.sassgestionstockapp.requests.RegisterTenantRequest;
import com.rachidy.sassgestionstockapp.responses.TenantResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TenantMapper {

    public Tenant toEntity(final RegisterTenantRequest request){
        return Tenant.builder()
                .companyCode(request.getCompanyCode())
                .companyName(request.getCompanyName())
                .email(request.getEmail())
                .adminFullName(request.getAdminFullName())
                .adminEmail(request.getAdminEmail())
                .adminPassword(request.getAdminPassword())
                .createdAt(LocalDateTime.now())
                .build();

    }

    public TenantResponse toResponse(final Tenant tenant){
        return TenantResponse.builder().companyCode(tenant.getCompanyCode())
                .tenantId(tenant.getId())
                .companyName(tenant.getCompanyName())
                .email(tenant.getEmail())
                .adminFullName(tenant.getAdminFullName())
                .adminEmail(tenant.getAdminEmail())
                .adminPassword(tenant.getAdminPassword())
                .createdAt(tenant.getCreatedAt())
                .build();
    }
}
