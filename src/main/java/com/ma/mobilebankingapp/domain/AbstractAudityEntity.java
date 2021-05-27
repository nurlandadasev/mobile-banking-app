package com.ma.mobilebankingapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAudityEntity<U> implements Serializable {

    private static final long serialVersionUID = 1L;


    @CreatedBy
    @Column(name = "created_by",length = 50, updatable = false)
    @JsonIgnore
    private U createdBy;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private Instant createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    private U lastModifiedBy;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    @JsonIgnore
    private Instant lastModifiedDate;


}
