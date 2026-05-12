package com.rachidy.sassgestionstockapp.responses;

import com.rachidy.sassgestionstockapp.entities.TenantStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {

    private String tenantId;
    private String companyName;
    private String companyCode;
    private String email;
    private TenantStatus tenantStatus;
    private String adminFullName;
    private String adminEmail;
    private String adminUsername;
    private String adminPassword;
    private LocalDateTime createdAt;


}
