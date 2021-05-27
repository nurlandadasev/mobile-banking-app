package com.ma.mobilebankingapp;

import com.ma.mobilebankingapp.domain.entities.Account;
import com.ma.mobilebankingapp.domain.entities.Currency;
import com.ma.mobilebankingapp.domain.entities.Customer;
import com.ma.mobilebankingapp.domain.repo.RepoAccount;
import com.ma.mobilebankingapp.domain.repo.RepoCurrency;
import com.ma.mobilebankingapp.domain.repo.RepoCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class MobileBankingAppApplication implements CommandLineRunner {

    private final RepoCurrency repoCurrency;
    private final RepoCustomer repoCustomer;
    private final RepoAccount repoAccount;


    public static void main(String[] args) {
        SpringApplication.run(MobileBankingAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repoCurrency.save(Currency.builder().currency("EUR").build());
        repoCurrency.save(Currency.builder().currency("USD").build());
        repoCurrency.save(Currency.builder().currency("MDL").build());

        repoCustomer.save(Customer.builder().customerUUID("721DF184-ADCF-431B-8332-5960B6497B14").name("TestUserName").surname("TestUserSurname").password("123456").username("SomeUsername2").build());
        repoCustomer.save(Customer.builder().customerUUID("13O2HJSA-CDGD-32GT-JU78-786HFYGDI89D").name("TestUserName2").surname("TestUserSurname2").password("123456").username("SomeUsername2").build());

    }



}
