package com.rachidy.sassgestionstockapp.entities;

import com.rachidy.sassgestionstockapp.config.TenantContext;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@FilterDef(
        name = "tenantFilter",
        parameters = @ParamDef(name = "tenantId",type = String.class),
        defaultCondition = "tenant_id = :tenantId"
)
@Filter(name = "tenantFilter")
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false,updatable = false)
    private String id;

    @Column(name = "tenant_id",nullable = false)
    private String tenantId;

    @CreatedDate
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at",insertable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by",nullable = false,updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by",insertable = false)
    private String updatedBy;

    @Column(name = "deleted",nullable = false)
    private Boolean deleted;

    @PrePersist
    protected void onCreate(){
        if(this.deleted == null){
            this.deleted = Boolean.FALSE;
        }
        if(this.createdBy == null){
            this.createdBy= "SYSTEM";
        }
        if(this.tenantId == null){
            this.tenantId = TenantContext.getCurrentTenant();
        }

    }
}
