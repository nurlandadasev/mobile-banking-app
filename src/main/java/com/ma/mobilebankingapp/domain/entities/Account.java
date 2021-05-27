package com.ma.mobilebankingapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ma.mobilebankingapp.domain.AbstractAudityEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "update account set is_deleted=1, is_active=0 where id_account=?")
public class Account extends AbstractAudityEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount;

    @Column(unique = true, nullable = false, updatable = false)
    private String accountNumber;

    private BigDecimal balance;

    @OneToOne
    private Currency currency;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    private boolean isDeleted;
    private boolean isActive;


    @PrePersist
    public void autoFill(){
        setActive(true);
        setDeleted(false);
    }
    @PreRemove
    private void preRemove(){
        this.isDeleted = true;
    }


}
