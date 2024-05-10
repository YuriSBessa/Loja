package com.loja.loja.Services;

import com.loja.loja.DTOs.CreatedWalletDTO;
import com.loja.loja.DTOs.WalletsDTO;
import com.loja.loja.Enum.AccountType;
import com.loja.loja.Models.Wallet;
import com.loja.loja.Repositories.WalletRepository;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    WalletRepository repository;

    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity<Wallet> createWallet(BigDecimal balance,
                                               AccountType accountType,
                                               String coin,
                                               Long userId
    ) {
        CreatedWalletDTO dto = new CreatedWalletDTO();

        Date dataAtual = new Date();
        Timestamp timestampAtual = new Timestamp(dataAtual.getTime());

        dto.setBalance(balance);
        dto.setAccountType(accountType);
        dto.setCreatedAt(timestampAtual);
        dto.setUpdatedAt(timestampAtual);
        dto.setCoin(coin);
        dto.setUserId(userId);

        Wallet wallet = modelMapper.map(dto, Wallet.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(wallet));
    }

    public List<BigDecimal> findTotalBalanceByUser() {
        var result = repository.findTotalBalance();
        return result;
    }

    public List<WalletsDTO> findAll(String firstName) {
        var result = repository.findAllWallet(firstName);
        return result;
    }

    public List<WalletsDTO> findTop10ByBalance() {
        var result = repository.findTop10ByBalance();
        return result;
    }

    public List<WalletsDTO> findTop5ByAccountType(AccountType accountType) {
        var result = repository.findTop5ByAccountType(accountType);
        return result;
    }

    public ResponseEntity<Object> editWallet(BigDecimal balance,
                                             AccountType accountType,
                                             String coin,
                                             Long id) {
        Optional<Wallet> walletObj = repository.findById(id);
        CreatedWalletDTO dto = new CreatedWalletDTO();

        Date dataAtual = new Date();
        Timestamp updatedAt = new Timestamp(dataAtual.getTime());

        if(walletObj.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carteira não encontrada");
        }

        dto.setBalance(balance);
        dto.setAccountType(accountType);
        dto.setCoin(coin);
        dto.setUpdatedAt(updatedAt);

        Wallet wallet = walletObj.get();
        modelMapper.map(dto, wallet);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(wallet));
    }

    public ResponseEntity<Object> deleteWallet(Long id) {
        Optional<Wallet> walletObj = repository.findById(id);

        if(walletObj.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carteira não encontrada");
        }

        repository.delete(walletObj.get());
        return ResponseEntity.status(HttpStatus.OK).body("Carteira deletada com sucesso");
    }

    public Optional<Wallet> findWalletByUserIdAndAccountType(Long userId,
                                                      AccountType accountType) {
        var result = repository.findWalletByUserIdAndAccountType(userId, accountType);
        return result;
    }
}
