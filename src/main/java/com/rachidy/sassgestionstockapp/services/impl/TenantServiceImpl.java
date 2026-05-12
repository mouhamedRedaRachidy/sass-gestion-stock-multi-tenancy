package com.rachidy.sassgestionstockapp.services.impl;

import com.rachidy.sassgestionstockapp.auth.repository.UserRepository;
import com.rachidy.sassgestionstockapp.common.PageResponse;
import com.rachidy.sassgestionstockapp.entities.Tenant;
import com.rachidy.sassgestionstockapp.entities.TenantStatus;
import com.rachidy.sassgestionstockapp.entities.User;
import com.rachidy.sassgestionstockapp.entities.UserRole;
import com.rachidy.sassgestionstockapp.exceptions.DuplicateResourceException;
import com.rachidy.sassgestionstockapp.mappers.TenantMapper;
import com.rachidy.sassgestionstockapp.repositories.TenantRepository;
import com.rachidy.sassgestionstockapp.requests.RegisterTenantRequest;
import com.rachidy.sassgestionstockapp.responses.TenantResponse;
import com.rachidy.sassgestionstockapp.services.ProvisioningTenantService;
import com.rachidy.sassgestionstockapp.services.TenantService;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final ProvisioningTenantService provisioningTenantService;

    @Override
    public void registerTenant(RegisterTenantRequest request) {
        // teste tenant already exist by code company
        if (this.tenantRepository.existsByCompanyCode(request.getCompanyCode())) {
            throw new DuplicateResourceException("deja company code utilise");
        }

        // test tenant already exist by email
        if (this.tenantRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Tenant email  already ");
        }

        Tenant tenant = this.tenantMapper.toEntity(request);
        tenant.setAdminPassword(encoder.encode(request.getAdminPassword()));

        this.tenantRepository.save(tenant);

    }

    @Override
    public void approveTenant(String tenantId) {

        // change status to active
        Tenant tenant = this.tenantRepository.findById(tenantId)
                .orElseThrow(() -> new EntityNotFoundException("Tenant not exist"));
        tenant.setStatus(TenantStatus.ACTIVE);
        // create schema
        // initial admin
        try{
            crateAdminAccount(tenant);

        }catch(IllegalArgumentException e){
            rollbackStatus(tenant);
        }
        crateAdminAccount(tenant);

    }

    @Override
    public void activeTenant(String tenantId) {
        Tenant tenant = this.tenantRepository.findById(tenantId)
                .orElseThrow(() -> new EntityNotFoundException("entity not"));

        if (tenant.getStatus() != TenantStatus.PENDING) {
            throw new InvalidRequestStateException("Invalide elle doit le statut pending ");
        }
        tenant.setStatus(TenantStatus.ACTIVE);
        this.tenantRepository.save(tenant);
    }

    @Override
    public void deactivateTenant(String tenantId) {
        Tenant tenant = this.tenantRepository.findById(tenantId)
                .orElseThrow(() -> new EntityNotFoundException("entity not"));

        if (tenant.getStatus() == TenantStatus.ACTIVE) {
            throw new InvalidRequestStateException("Invalide elle doit le statut ACtive ");
        }
        tenant.setStatus(TenantStatus.INACTIVE);
        this.tenantRepository.save(tenant);
    }

    @Override
    public void suspendTenant(String tenantId) {
        Tenant tenant = this.tenantRepository.findById(tenantId)
                .orElseThrow(() -> new EntityNotFoundException("entity not"));

        if (tenant.getStatus() != TenantStatus.ACTIVE) {
            throw new InvalidRequestStateException("Invalide elle doit le statut  ");
        }
        tenant.setStatus(TenantStatus.SUSPENDED);
        this.tenantRepository.save(tenant);
    }

    @Override
    public PageResponse<TenantResponse> findAll(final int page, final int size) {
        final PageRequest request = PageRequest.of(page, size);
        final Page<Tenant> tenants = this.tenantRepository.findAll(request);
        final Page<TenantResponse> responses = tenants.map(tenantMapper::toResponse);
        return PageResponse.<TenantResponse>of(responses);
    }

    private void crateAdminAccount(final Tenant tenant) {
        // check user already exist by username
        if(this.userRepository.existsByUsername(tenant.getAdminUsername())){
            throw new DuplicateResourceException("already tenant exist");
        };

        final User user=User.builder()
                .username(tenant.getAdminUsername())
                .email(tenant.getAdminEmail())
                .firstName(extractFirstName(tenant.getAdminFullName()))
                .lastName(extractLastName(tenant.getAdminFullName()))
                .password(this.encoder.encode(tenant.getAdminPassword()))
                .tenant(tenant)
                .role(UserRole.PLATFORM_ADMIN)
        .build();
    }

    private String extractLastName(String fullName) {
        return fullName.split(" ")[0];
    }

    private String extractFirstName(String fullName) {
        return fullName.split(" ").length >1 ? fullName.split(" ")[1] : fullName;
    }

    private void rollbackStatus(Tenant tenant) {
        tenant.setStatus(TenantStatus.PENDING);
        this.tenantRepository.save(tenant);

    }
}
