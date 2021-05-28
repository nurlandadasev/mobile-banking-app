package com.ma.mobilebankingapp;

import com.ma.mobilebankingapp.domain.entities.Currency;
import com.ma.mobilebankingapp.domain.entities.Customer;
import com.ma.mobilebankingapp.domain.repo.RepoCurrency;
import com.ma.mobilebankingapp.domain.repo.RepoCustomer;
import com.ma.mobilebankingapp.services.impl.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class MobileBankingAppApplication implements CommandLineRunner {

    private final RepoCurrency repoCurrency;
    private final RepoCustomer repoCustomer;
    private final AccountServiceImpl accountService;

    public MobileBankingAppApplication(RepoCurrency repoCurrency, RepoCustomer repoCustomer, AccountServiceImpl accountService) {
        this.repoCurrency = repoCurrency;
        this.repoCustomer = repoCustomer;
        this.accountService = accountService;
    }


    public static void main(String[] args) {
        SpringApplication.run(MobileBankingAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Create data for testing project.
        repoCurrency.save(Currency.builder().currency("EUR").build());
        repoCurrency.save(Currency.builder().currency("USD").build());
        repoCurrency.save(Currency.builder().currency("MDL").build());

        repoCustomer.save(Customer.builder().customerUUID("721DF184-ADCF-431B-8332-5960B6497B14").name("TestUserName").surname("TestUserSurname").password("123456").username("SomeUsername2").build());
        repoCustomer.save(Customer.builder().customerUUID("13O2HJSA-CDGD-32GT-JU78-786HFYGDI89D").name("TestUserName2").surname("TestUserSurname2").password("123456").username("SomeUsername2").build());
        log.info("accountNUmber: {}",accountService.createAndSaveAccountNumber());
    }



}
