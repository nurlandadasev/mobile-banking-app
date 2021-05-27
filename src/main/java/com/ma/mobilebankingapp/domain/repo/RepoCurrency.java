package com.ma.mobilebankingapp.domain.repo;

import com.ma.mobilebankingapp.domain.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoCurrency extends JpaRepository<Currency,Long> {
}
