package com.ma.mobilebankingapp.services;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {


    AccountDto createAccount(AccountRequest accountRequest);

    void deleteAccount(long idAccount);

    AccountDto updateAccount(AccountRequest accountRequest);

    ResponseEntity<List<AccountDto>> getAccounts(String customerUUID);


}
