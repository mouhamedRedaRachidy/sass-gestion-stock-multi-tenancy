package com.rachidy.sassgestionstockapp.repositories;

import com.rachidy.sassgestionstockapp.entities.Tenant;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant,String> {

    boolean existsByCompanyCode(String companyCode);
    boolean existsByEmail(String email);
}
