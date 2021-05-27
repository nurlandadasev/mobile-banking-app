package com.ma.mobilebankingapp.services;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;
import com.ma.mobilebankingapp.domain.dto.BalanceUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {


    AccountDto createAccount(AccountRequest accountRequest);

    void deleteAccount(String accountNumber);
    void activeInactive(String accountNumber,Boolean active);

    AccountDto updateAccount(BalanceUpdateRequest updateRequest);

    ResponseEntity<List<AccountDto>> getAccounts(String customerUUID);


}
