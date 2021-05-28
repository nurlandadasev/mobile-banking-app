package com.ma.mobilebankingapp.api;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;
import com.ma.mobilebankingapp.domain.dto.BalanceUpdateRequest;
import com.ma.mobilebankingapp.domain.entities.Account;
import com.ma.mobilebankingapp.services.impl.AccountServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Api(produces = "json", consumes = "json", tags = "Banking Controller")
@RequestMapping("/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(
            tags = "Banking Controller",
            value = "Create account.",
            notes = "You can create invoices for clients with this api.\n"+
                    "To test the API, you can use this data:\n" +
                    "CustomerUUID: 721DF184-ADCF-431B-8332-5960B6497B14 or 13O2HJSA-CDGD-32GT-JU78-786HFYGDI89D\n" +
                    "CurrencyIds(List): (1,2,3) 1 -EUR,2 - USD,3 - MDL\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "Account created successfully."),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "Customer or currency not found.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestBody AccountRequest accountRequest){
        return accountService.createAccount(accountRequest);
    }


    @ApiOperation(
            tags = "Banking Controller",
            value = "Update account.",
            notes = "You can update the balance in your bank account. Enter your account number.\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "Account updated successfully."),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "Account not found.")
    })
    @PutMapping
    public AccountDto updateAccountBalance(@RequestBody BalanceUpdateRequest balanceUpdateRequest){
        return accountService.updateAccount(balanceUpdateRequest);
    }


    @ApiOperation(
            tags = "Banking Controller",
            value = "Activate or deactivate account.",
            notes = "You can activate or deactivate bank account."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "Unable to change the status of a deleted account.")
    })
    @PutMapping("/{accountNumber}/{active}")
    public void activeInactiveAccount(@PathVariable String accountNumber,@PathVariable Boolean active){
        accountService.activeInactive(accountNumber,active);
    }




    @ApiOperation(
            tags = "Banking Controller",
            value = "Get accounts with filtering.",
            notes = "Search invoices by criteria. \n" +
                    "You can search accounts by creation time by selecting two dates.\n" +
                    "CurrencyIdList - Select the id's of the currencies you want to search.\n" +
                    "Select active(true,false) for searching by status.\n" +
                    "Search by customer uuid.\n" +
                    "To test the API, you can use this data:\n" +
                    "CustomerUUID: 721DF184-ADCF-431B-8332-5960B6497B14 or 13O2HJSA-CDGD-32GT-JU78-786HFYGDI89D\n" +
                    "CurrencyIds(List): (1,2,3) 1 -EUR,2 - USD,3 - MDL"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "Success."),
            @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "No accounts found.")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<AccountDto>> getAccountsWithFiltering(@ApiParam(value = "Customer UUID") @RequestParam(required = false) String customerUUID,
                                                                     @ApiParam(value = "Currency id's list. Example: 1,2,3") @RequestParam(required = false) List<Long> currencyIds,
                                                                     @ApiParam(value = "Get by isActive. true false") @RequestParam(required = false) Boolean isActive,
                                                                     @ApiParam(value = "Date format: yyyy-mm-dd") @RequestParam(required = false) LocalDate startDate,
                                                                     @ApiParam(value = "Date format: yyyy-mm-dd") @RequestParam(required = false) LocalDate finishDate){
    return accountService.getAccountsWithFiltering(customerUUID,currencyIds,isActive,startDate,finishDate)
            .map(accounts -> new ResponseEntity<>(accounts,HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(null,HttpStatus.NO_CONTENT));
    }



    @ApiOperation(
            tags = "Banking Controller",
            value = "Get all customer accounts.",
            notes = "Enter customer uuid to take accounts."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "Success."),
            @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "No accounts found. Please create accounts and after return to this api.")
    })
    @GetMapping("/{customerUUID}")
    public ResponseEntity<List<Account>> getAllAccountsForCustomer(@PathVariable String customerUUID){
        return accountService.getAccounts(customerUUID)
                .map(accounts -> new ResponseEntity<>(accounts,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null,HttpStatus.NO_CONTENT));
    }




    @ApiOperation(
            tags = "Banking Controller",
            value = "Delete account.",
            notes = "Enter account number to delete."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "Success."),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = AccountDto.class)), description = "Account not found to delete.")
    })
    @DeleteMapping("/{accountNumber}")
    public void deleteAccount(@PathVariable String accountNumber){
        accountService.deleteAccount(accountNumber);
    }









}
