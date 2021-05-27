package com.ma.mobilebankingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceUpdateRequest {

    @NotBlank(message = "Account number cannot be empty or null.")
    private String accountNumber;
    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;
}
