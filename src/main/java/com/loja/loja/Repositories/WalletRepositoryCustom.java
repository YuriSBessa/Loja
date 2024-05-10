package com.loja.loja.Repositories;

import com.loja.loja.DTOs.WalletsDTO;
import com.loja.loja.Enum.AccountType;
import com.loja.loja.Models.Wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WalletRepositoryCustom {
    List<BigDecimal> findTotalBalance();
    List<WalletsDTO> findAllWallet(String firstName);
    List<WalletsDTO> findTop10ByBalance();
    List<WalletsDTO> findTop5ByAccountType(AccountType accountType);
    Optional<Wallet> findWalletByUserIdAndAccountType(Long userId, AccountType accountType);
}
