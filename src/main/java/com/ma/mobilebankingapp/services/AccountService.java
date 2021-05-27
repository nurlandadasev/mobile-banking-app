package com.ma.mobilebankingapp.services;

import com.ma.mobilebankingapp.domain.dto.AccountDto;

import java.util.List;

public interface AccountService {


    AccountDto createAccount(AccountDto accountDto);

    void deleteAccount(long idAccount);

    AccountDto updateAccount(AccountDto accountDto);

    List<AccountDto> getAccounts(Long idCustomer);


}
