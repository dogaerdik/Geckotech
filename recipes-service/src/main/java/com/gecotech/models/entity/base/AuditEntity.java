package com.gecotech.models.entity.base;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity<U> {
    @CreatedBy
    private U createdBy;
    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date creationDate;
    @LastModifiedBy
    private U lastModifiedBy;
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastModifiedDate;
}