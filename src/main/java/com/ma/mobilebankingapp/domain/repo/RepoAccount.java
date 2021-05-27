package com.ma.mobilebankingapp.domain.repo;

import com.ma.mobilebankingapp.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoAccount extends JpaRepository<Account,Long> {


    Optional<Account> findByAccountNumber(String accountNumber);


    @Query("select a from Account a where a.customer.customerUUID=:customerUUID")
    Optional<List<Account>> findAccountsByCustomerUUID(@Param("customerUUID") String uuid);

}
