package com.ma.mobilebankingapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ma.mobilebankingapp.domain.AbstractAudityEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Where(clause = "is_deleted=false")
@SQLDelete(sql = "update customer set is_deleted=true where id=?")
public class Customer extends AbstractAudityEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;

    @Column(name = "UUID", unique = true, nullable = false, updatable = false)
    private String customerUUID = UUID.randomUUID().toString().toUpperCase();

    private String name;
    private String surname;

    private String username;
    private String password;

    private boolean isDeleted;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Account> accountList = new ArrayList<>();


    @PreRemove
    private void preRemove() {
        this.isDeleted = false;
    }

}
