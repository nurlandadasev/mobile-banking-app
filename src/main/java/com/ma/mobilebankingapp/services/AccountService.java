package com.ma.mobilebankingapp.services;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;
import com.ma.mobilebankingapp.domain.dto.BalanceUpdateRequest;
import com.ma.mobilebankingapp.domain.entities.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountService {


    AccountDto createAccount(AccountRequest accountRequest);

    void deleteAccount(String accountNumber);
    void activeInactive(String accountNumber,Boolean active);

    AccountDto updateAccount(BalanceUpdateRequest updateRequest);

    Optional<List<Account>> getAccounts(String customerUUID);


    Optional<List<AccountDto>> getAccountsWithFiltering(String customerUUID, List<Long> currencyIds, Boolean isActive, LocalDate startDate, LocalDate finishDate);


}
