package com.loja.loja.DTOs;

import com.loja.loja.Enum.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatedWalletDTO {

    private BigDecimal balance;
    private AccountType accountType;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String coin;
    private Long userId;
}
