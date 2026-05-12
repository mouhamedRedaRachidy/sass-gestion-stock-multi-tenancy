package com.rachidy.sassgestionstockapp.services.impl;

import com.rachidy.sassgestionstockapp.entities.Tenant;
import com.rachidy.sassgestionstockapp.exceptions.TenantProvisonException;
import com.rachidy.sassgestionstockapp.services.ProductService;
import com.rachidy.sassgestionstockapp.services.ProvisioningTenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProvisioningTenantServiceImpl implements ProvisioningTenantService {

    private JdbcTemplate jdbcTemplate;

    @Override
    public void provisioningTenant(Tenant tenant) {
        final String schemaName="tenant "+tenant.getCompanyName().toLowerCase();

        try{
            // create postgres schema
            createSchema(schemaName);
            log.info("create schema {} completed", schemaName);
            // run flyway migration
            runMigrationFlyway(schemaName);
            log.info("run migration de schema {} completed",schemaName);
            // init mock data
        }catch (Exception e){

            log.error("error creation schema");

            try{
                dropSchema(schemaName);
            }catch (Exception exp){
                throw new TenantProvisonException("Tenant provision error");
            }
        }
    }

    private void dropSchema(String schemaName) {
        final String sql=String.format("DROP SCHEMA %s CASCADE",schemaName);
        jdbcTemplate.execute(sql);
    }

    private void runMigrationFlyway(String schemaName){
        Flyway flyway=Flyway.configure()
                .dataSource(this.jdbcTemplate.getDataSource())
                .schemas(schemaName)
                .locations("classpath:db/migration/tenant")
                .baselineOnMigrate(true)
                .validateOnMigrate(true)
                .cleanDisabled(true)
                .load();

        flyway.migrate();
    }

    private void createSchema(String schemaName) {
        final String sql=String.format("CREATE SCHEMA IF NOT EXIST %s ",schemaName);
        this.jdbcTemplate.execute(sql);
    }
}
