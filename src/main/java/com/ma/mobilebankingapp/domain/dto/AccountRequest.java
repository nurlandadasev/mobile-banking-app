package com.ma.mobilebankingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequest {

    private Long idAccount;
    private long idCurrency;
    private BigDecimal balance;
    private Long idCustomer;

}
