package com.loja.loja.DTOs;

import com.loja.loja.Enum.AccountType;
import com.loja.loja.Utils.ConverterUtilities;
import com.loja.loja.Utils.DateUtilities;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Data
public class WalletsDTO {
    private String firstName;
    private String lastName;
    private BigDecimal balance;
    private String accountType;
    private String coin;
    private String createdAt;
    private String updatedAt;

    public WalletsDTO(String firstName, String lastName, BigDecimal balance, AccountType accountType, String coin, Date createdAt, Date updatedAt){
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.accountType = ConverterUtilities.ConvertAccountTypeToString(accountType);
        this.coin = coin;
        this.createdAt = DateUtilities.DateFormater(createdAt);
        this.updatedAt = DateUtilities.DateFormater(createdAt);
    }
}
