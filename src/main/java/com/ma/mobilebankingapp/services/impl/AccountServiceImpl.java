package com.ma.mobilebankingapp.services.impl;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;
import com.ma.mobilebankingapp.domain.entities.Account;
import com.ma.mobilebankingapp.domain.entities.Currency;
import com.ma.mobilebankingapp.domain.entities.Customer;
import com.ma.mobilebankingapp.domain.repo.RepoAccount;
import com.ma.mobilebankingapp.domain.repo.RepoCurrency;
import com.ma.mobilebankingapp.domain.repo.RepoCustomer;
import com.ma.mobilebankingapp.exceptions.NotFoundException;
import com.ma.mobilebankingapp.services.AccountService;
import com.ma.mobilebankingapp.services.mappers.AccountMapper;
import com.ma.mobilebankingapp.utilities.RandomNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final RepoAccount repoAccount;
    private final RepoCurrency repoCurrency;
    private final RepoCustomer repoCustomer;


    @Override
    public AccountDto createAccount(AccountRequest accountRequest) {
        Account account = new Account();
        account.setAccountNumber(createAndSaveAccountNumber());
        account.setBalance(accountRequest.getBalance());
        account.setCurrency(validateCurrency(accountRequest.getIdCurrency()));
        account.setCustomer(validateCustomer(accountRequest.getIdCustomer()));
        return AccountMapper.INSTANCE.mapToDto(account);
    }


    private Customer validateCustomer(long idCustomer){
        return repoCustomer.findById(idCustomer).orElseThrow(() -> new NotFoundException(String.format("Customer not found by this id: %s",idCustomer)));
    }

    private Currency validateCurrency(long idCurrency){
       return repoCurrency.findById(idCurrency).orElseThrow(() -> new NotFoundException(String.format("Currency not found by this id: %s",idCurrency)));
    }

    private String createAndSaveAccountNumber(){
        String randomNumber = RandomNumber.generateRandomString(20);
        if (repoAccount.findByAccountNumber(randomNumber).isPresent())
            createAndSaveAccountNumber();

        return randomNumber;
    }

    @Override
    public void deleteAccount(long idAccount) {
        repoAccount.deleteById(idAccount);
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {
        Account account = repoAccount.findById(accountDto.getIdAccount())
                .orElseThrow(() -> new NotFoundException(String.format("Account not found by this id: %s", accountDto.getIdAccount())));
        BigDecimal balance = account.getBalance();
        BigDecimal newBalance = balance.add(accountDto.getBalance());
        account.setBalance(newBalance);
        return AccountMapper.INSTANCE.mapToDto(account);
    }

    @Override
    public List<AccountDto> getAccounts(Long idCustomer) {
        List<Account> accountList = repoAccount.findAll();
        return AccountMapper.INSTANCE.mapListToDtoList(accountList);
    }
}
