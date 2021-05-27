package com.ma.mobilebankingapp.domain.repo;

import com.bank.banktest.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoCustomer extends JpaRepository<Customer,Long> {
}
