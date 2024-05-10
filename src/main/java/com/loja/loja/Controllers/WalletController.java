package com.loja.loja.Controllers;

import com.loja.loja.DTOs.WalletsDTO;
import com.loja.loja.Enum.AccountType;
import com.loja.loja.Models.Wallet;
import com.loja.loja.Services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletService service;

    @PostMapping("/create-wallet")
    public ResponseEntity<Wallet> createNewWallet(
            @RequestParam(required = true, value = "balance", defaultValue = "") BigDecimal balance,
            @RequestParam(required = true, value = "accountType", defaultValue = "") AccountType accountType,
            @RequestParam(required = true, value = "coin", defaultValue = "") String coin,
            @RequestParam(required = true, value = "userId", defaultValue = "") Long userId
    ){
        return service.createWallet(balance, accountType, coin, userId);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<WalletsDTO>> getAll(
            @RequestParam(required = false, value = "firstName", defaultValue = "") String firstName
    ){
        return new ResponseEntity<>(service.findAll(firstName), HttpStatus.OK);
    }

    @GetMapping("/find-total-balance-by-account-type")
    public ResponseEntity<List<BigDecimal>> getTotalBalanceByUser(){
        return new ResponseEntity<>(service.findTotalBalanceByUser(), HttpStatus.OK);
    }

    @GetMapping("/top-balance")
    public ResponseEntity<List<WalletsDTO>> getTop10ByBalance(){
        return new ResponseEntity<>(service.findTop10ByBalance(), HttpStatus.OK);
    }

    @GetMapping("/top-by-account-type")
    public ResponseEntity<List<WalletsDTO>> getTop5WalletsByAccountType(
            @RequestParam(required = true, value = "accountType", defaultValue = "") AccountType accountType
    ){
        return new ResponseEntity<>(service.findTop5ByAccountType(accountType), HttpStatus.OK);
    }

    @GetMapping("/wallet-by-user-id-and-account-type")
    public ResponseEntity<Optional<Wallet>> getWalletByUserIdAndAccountType(
            @RequestParam(required = true, value = "userId") Long userId,
            @RequestParam(required = true, value = "accountType", defaultValue = "") AccountType accountType
    ){
        return new ResponseEntity<>(service.findWalletByUserIdAndAccountType(userId, accountType), HttpStatus.OK);
    }

    @PutMapping("/edit-wallet/{id}")
    public ResponseEntity<Object> editWallet(
            @RequestParam(required = false, value = "balance") BigDecimal balance,
            @RequestParam(required = false, value = "accountType") AccountType accountType,
            @RequestParam(required = false, value = "coin") String coin,
            @PathVariable(required = true, value = "id") Long id
    ){
        return service.editWallet(balance, accountType, coin, id);
    }

    @DeleteMapping("/delete-wallet/{id}")
    public ResponseEntity<Object> deleteWallet(
            @PathVariable(required = true, value = "id") Long id
    ){
        return service.deleteWallet(id);
    }
}
