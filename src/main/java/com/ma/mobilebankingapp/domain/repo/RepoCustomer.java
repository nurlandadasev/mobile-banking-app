package com.ma.mobilebankingapp.domain.repo;

import com.ma.mobilebankingapp.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoCustomer extends JpaRepository<Customer,Long> {

    Optional<Customer> findByCustomerUUID(String customerUUID);

}
