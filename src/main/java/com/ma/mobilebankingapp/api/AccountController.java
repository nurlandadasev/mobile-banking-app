package com.ma.mobilebankingapp.api;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;
import com.ma.mobilebankingapp.services.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountServiceImpl accountService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestBody AccountRequest accountRequest){
        return accountService.createAccount(accountRequest);
    }


    @PutMapping
    public AccountDto updateAccount(@RequestBody AccountRequest accountRequest){
        return accountService.updateAccount(accountRequest);
    }


    @GetMapping("/{customerUUID}")
    public ResponseEntity<List<AccountDto>> getAllAccountsForCustomer(@PathVariable String customerUUID){
        return accountService.getAccounts(customerUUID);
    }




}
