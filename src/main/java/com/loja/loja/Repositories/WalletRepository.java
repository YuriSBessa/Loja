package com.loja.loja.Repositories;

import com.loja.loja.Models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>, WalletRepositoryCustom {

}
