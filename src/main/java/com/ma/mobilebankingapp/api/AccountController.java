package com.ma.mobilebankingapp.api;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;
import com.ma.mobilebankingapp.domain.dto.BalanceUpdateRequest;
import com.ma.mobilebankingapp.services.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestBody AccountRequest accountRequest){
        return accountService.createAccount(accountRequest);
    }


    @PutMapping
    public AccountDto updateAccountBalance(@RequestBody BalanceUpdateRequest balanceUpdateRequest){
        return accountService.updateAccount(balanceUpdateRequest);
    }

    @PutMapping("/{activeDeactivateAccount}/{active}")
    public void activeInactiveAccount(@PathVariable String activeDeactivateAccount,@PathVariable Boolean active){
        accountService.activeInactive(activeDeactivateAccount,active);
    }


    @GetMapping("/{customerUUID}")
    public ResponseEntity<List<AccountDto>> getAllAccountsForCustomer(@PathVariable String customerUUID){
        return accountService.getAccounts(customerUUID);
    }


    @DeleteMapping("/{accountNumber}")
    public void deleteAccount(@PathVariable String accountNumber){
        accountService.deleteAccount(accountNumber);
    }









}
