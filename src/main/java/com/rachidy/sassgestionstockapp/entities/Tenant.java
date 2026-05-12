package com.rachidy.sassgestionstockapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
//@Entity
@Table(name = "tenants")
public class Tenant extends AbstractEntity{

    @Column(name = "company_name",nullable = false,unique = true)
    private String companyName;
    @Column(name = "company_code",nullable = false,unique = true)
    private String companyCode;
    @Column(name = "company_email",nullable = false,unique = true)
    private String email;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "tenant_status",nullable = false)
    private TenantStatus status=TenantStatus.PENDING;
    //security  tenant

    @Column(name = "admin_full_name",nullable = false)
    private String adminFullName;
    @Column(name = "admin_email",nullable = false,unique = true)
    private String adminEmail;
    @Column(name = "admin_username",nullable = false,unique = true)
    private String adminUsername;
    @Column(name = "admin_password",nullable = false)
    private String adminPassword;

}
