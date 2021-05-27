package com.ma.mobilebankingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequest {

    private Long idAccount;
    private long idCurrency;
    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;
    private Long idCustomer;

}
