package com.ma.mobilebankingapp;

import com.ma.mobilebankingapp.domain.entities.Currency;
import com.ma.mobilebankingapp.domain.entities.Customer;
import com.ma.mobilebankingapp.domain.repo.RepoCurrency;
import com.ma.mobilebankingapp.domain.repo.RepoCustomer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MobileBankingAppApplication implements CommandLineRunner {

    private final RepoCurrency repoCurrency;
    private final RepoCustomer repoCustomer;

    public MobileBankingAppApplication(RepoCurrency repoCurrency, RepoCustomer repoCustomer) {
        this.repoCurrency = repoCurrency;
        this.repoCustomer = repoCustomer;
    }

    public static void main(String[] args) {
        SpringApplication.run(MobileBankingAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repoCurrency.save(Currency.builder().currency("EUR").build());
        repoCurrency.save(Currency.builder().currency("USD").build());
        repoCurrency.save(Currency.builder().currency("MDL").build());

        repoCustomer.save(Customer.builder().name("TestUserName").surname("TestUserSurname").password("123456").username("SomeUsername").build());
    }



}
