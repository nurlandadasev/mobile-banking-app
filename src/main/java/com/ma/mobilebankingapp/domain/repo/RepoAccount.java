package com.ma.mobilebankingapp.domain.repo;

import com.ma.mobilebankingapp.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoAccount extends JpaRepository<Account,Long> {
}