package com.ma.mobilebankingapp.services.impl;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;
import com.ma.mobilebankingapp.domain.dto.BalanceUpdateRequest;
import com.ma.mobilebankingapp.domain.entities.Account;
import com.ma.mobilebankingapp.domain.entities.Currency;
import com.ma.mobilebankingapp.domain.entities.Customer;
import com.ma.mobilebankingapp.domain.repo.RepoAccount;
import com.ma.mobilebankingapp.domain.repo.RepoCurrency;
import com.ma.mobilebankingapp.domain.repo.RepoCustomer;
import com.ma.mobilebankingapp.exceptions.BadRequestException;
import com.ma.mobilebankingapp.exceptions.NotFoundException;
import com.ma.mobilebankingapp.services.AccountService;
import com.ma.mobilebankingapp.services.mappers.AccountMapper;
import com.ma.mobilebankingapp.utilities.RandomNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
        repoAccount.save(account);
        return AccountMapper.INSTANCE.mapToDto(account);
    }


    private Customer validateCustomer(long idCustomer){
        return repoCustomer.findById(idCustomer).orElseThrow(() -> new BadRequestException(String.format("Customer not found by this id: %s",idCustomer)));
    }

    private Currency validateCurrency(long idCurrency){
       return repoCurrency.findById(idCurrency).orElseThrow(() -> new BadRequestException(String.format("Currency not found by this id: %s",idCurrency)));
    }

    private String createAndSaveAccountNumber(){
        String randomNumber = RandomNumber.generateRandomString(20);
        if (repoAccount.findByAccountNumber(randomNumber).isPresent())
            createAndSaveAccountNumber();

        return randomNumber;
    }

    @Override
    public void deleteAccount(String accountNumber) {
        Account account = repoAccount.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BadRequestException("Account not found for delete."));
        repoAccount.delete(account);
    }

    @Override
    public void activeInactive(String accountNumber,Boolean active) {
        Account account = repoAccount.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BadRequestException("Account not found."));
        account.setActive(active);
        repoAccount.save(account);
    }

    @Override
    public AccountDto updateAccount(BalanceUpdateRequest balanceUpdateRequest) {
        Account account = repoAccount.findByAccountNumber(balanceUpdateRequest.getAccountNumber())
                .orElseThrow(() -> new BadRequestException("Account not found."));
        BigDecimal balance = account.getBalance();
        BigDecimal newBalance = balance.add(balanceUpdateRequest.getBalance());
        account.setBalance(newBalance);
        repoAccount.save(account);
        return AccountMapper.INSTANCE.mapToDto(account);
    }

    @Override
    public ResponseEntity<List<AccountDto>> getAccounts(String customerUUID) {
        Optional<List<Account>> accountList = repoAccount.findAccountsByCustomerUUID(customerUUID);
        return accountList.map(accounts -> new ResponseEntity<>(AccountMapper.INSTANCE.mapListToDtoList(accounts), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null,HttpStatus.NO_CONTENT));
    }
}
