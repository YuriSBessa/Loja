package com.loja.loja.Utils;

import com.loja.loja.Enum.AccountType;

public class ConverterUtilities {

    public static String ConvertAccountTypeToString(AccountType accountType){
        if(AccountType.CHECKING_ACCOUNT.equals(accountType)){
            return "Conta Corrente";
        } else if(AccountType.SAVINGS_ACCOUNT.equals(accountType)){
            return "Conta Poupança";
        } else{
            return "-";
        }
    }
}
