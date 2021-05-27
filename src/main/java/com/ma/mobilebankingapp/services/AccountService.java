package com.ma.mobilebankingapp.services;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;

import java.util.List;

public interface AccountService {


    AccountDto createAccount(AccountRequest accountRequest);

    void deleteAccount(long idAccount);

    AccountDto updateAccount(AccountDto accountDto);

    List<AccountDto> getAccounts(Long idCustomer);


}
