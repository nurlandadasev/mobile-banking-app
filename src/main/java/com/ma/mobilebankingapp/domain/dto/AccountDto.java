package com.ma.mobilebankingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDto {

    private Long idAccount;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private boolean isActive;
    private Instant lastModifiedDate;

}
