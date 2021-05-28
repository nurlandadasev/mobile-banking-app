package com.ma.mobilebankingapp.services.impl;

import com.ma.mobilebankingapp.domain.dto.AccountDto;
import com.ma.mobilebankingapp.domain.dto.AccountRequest;
import com.ma.mobilebankingapp.domain.dto.BalanceUpdateRequest;
import com.ma.mobilebankingapp.domain.entities.*;
import com.ma.mobilebankingapp.domain.repo.RepoAccount;
import com.ma.mobilebankingapp.domain.repo.RepoCurrency;
import com.ma.mobilebankingapp.domain.repo.RepoCustomer;
import com.ma.mobilebankingapp.exceptions.BadRequestException;
import com.ma.mobilebankingapp.services.AccountService;
import com.ma.mobilebankingapp.services.mappers.AccountMapper;
import com.ma.mobilebankingapp.utilities.RandomNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {


    private final RepoAccount repoAccount;
    private final RepoCurrency repoCurrency;
    private final RepoCustomer repoCustomer;
    private final EntityManager entityManager;

    public AccountServiceImpl(RepoAccount repoAccount, RepoCurrency repoCurrency, RepoCustomer repoCustomer, EntityManager entityManager) {
        this.repoAccount = repoAccount;
        this.repoCurrency = repoCurrency;
        this.repoCustomer = repoCustomer;
        this.entityManager = entityManager;
    }

    @Override
    public AccountDto createAccount(AccountRequest accountRequest) {
        Account account = new Account();
        account.setCustomer(validateCustomer(accountRequest.getCustomerUUID()));
        account.setBalance(accountRequest.getBalance());
        account.setCurrency(validateCurrency(accountRequest.getIdCurrency()));
        account.setAccountNumber(createAndSaveAccountNumber());
        repoAccount.save(account);
        return AccountMapper.INSTANCE.mapToDto(account);
    }


    private Customer validateCustomer(String customerUUID) {
        return repoCustomer.findByCustomerUUID(customerUUID)
                .orElseThrow(() -> new BadRequestException(String.format("Customer not found by this uuid: %s", customerUUID)));
    }

    private Currency validateCurrency(long idCurrency) {
        return repoCurrency.findById(idCurrency).orElseThrow(() -> new BadRequestException(String.format("Currency not found by this id: %s", idCurrency)));
    }

    public String createAndSaveAccountNumber() {
        boolean done  = true;
        String randomNumber = null;
        while(done) {
            randomNumber = RandomNumber.generateRandomString(20);
            if(repoAccount.findByAccountNumber(randomNumber).isEmpty()) {
                done = false;
            }
        }
        return randomNumber;
    }

    @Override
    public void deleteAccount(String accountNumber) {
        Account account = repoAccount.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BadRequestException("Account not found for delete."));
        repoAccount.delete(account);
    }

    @Override
    public void activeInactive(String accountNumber, Boolean active) {
        Account account = repoAccount.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BadRequestException("Account not found."));
        if (account.isDeleted())
            throw new BadRequestException("Unable to change the status of a deleted account.");
        account.setActive(active);
        repoAccount.save(account);
    }

    @Override
    public AccountDto updateAccount(BalanceUpdateRequest balanceUpdateRequest) {
        Account account = repoAccount.findByAccountNumber(balanceUpdateRequest.getAccountNumber())
                .orElseThrow(() -> new BadRequestException("Account not found."));
        account.setBalance(balanceUpdateRequest.getBalance());
        repoAccount.save(account);
        return AccountMapper.INSTANCE.mapToDto(account);
    }

    @Override
    public Optional<List<Account>> getAccounts(String customerUUID) {

        return repoAccount.findAccountsByCustomerUUID(customerUUID);
    }

    @Override
    public Optional<List<AccountDto>> getAccountsWithFiltering(String customerUUID, List<Long> currencyIds, Boolean isActive, LocalDate startDate, LocalDate finishDate) {
        log.info("parameters: {},{},{},{},{}",customerUUID,currencyIds,isActive,startDate,finishDate);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> root = cq.from(Account.class);

        List<Predicate> predicateList = new ArrayList<>();

        if (customerUUID != null && !customerUUID.equals("null") && !customerUUID.isBlank()) {
            predicateList.add(cb.equal(root.join(Account_.CUSTOMER, JoinType.INNER).get(Customer_.CUSTOMER_UU_ID), customerUUID));
        }

        if (currencyIds != null && !currencyIds.isEmpty()) {
            predicateList.add(root.join(Account_.CURRENCY, JoinType.INNER).get(Currency_.ID_CURRENCY).in(currencyIds));
        }

        if (isActive != null) {
            predicateList.add(cb.equal(root.get(Account_.IS_ACTIVE), isActive));
        }

        if (startDate != null & finishDate != null) {
            predicateList.add(cb.between(root.get(Account_.CREATED_DATE), startDate.atStartOfDay().toInstant(ZoneOffset.UTC),
                    finishDate.atTime(23, 59, 59).toInstant(ZoneOffset.UTC)));
        }

        cq.where(predicateList.toArray(new Predicate[predicateList.size()]));

        cq.orderBy(cb.desc(root.get(Account_.CREATED_DATE)));
        List<Account> resultList = entityManager.createQuery(cq).getResultList();

        return Optional.ofNullable(AccountMapper.INSTANCE.mapListToDtoList(resultList));
    }
}
