package com.rachidy.sassgestionstockapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false,updatable = false)
    private String id;

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

    }
}
