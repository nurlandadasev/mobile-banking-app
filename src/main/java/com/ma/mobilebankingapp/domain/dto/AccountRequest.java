package com.ma.mobilebankingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequest {

    @Positive(message = "idCurrency cannot be zero or negative.")
    private long idCurrency;
    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;
    @NotBlank(message = "Customer UUID cannot be empty")
    @NotNull(message = "Customer UUID cannot be empty")
    private String customerUUID;

}
