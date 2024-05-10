package com.loja.loja.Models;

import com.loja.loja.Enum.AccountType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "wallet")
@Data
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq_generator")
    @SequenceGenerator(name = "wallet_seq_generator", sequenceName = "wallet_seq", allocationSize = 1)
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "coin")
    private String coin;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    public Wallet(Long id, BigDecimal balance, AccountType accountType, Timestamp createdAt, Timestamp updatedAt, String coin, User userId) {
        this.id = id;
        this.balance = balance;
        this.accountType = accountType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.coin = coin;
        this.userId = userId;
    }

    public Wallet(){

    }
}
