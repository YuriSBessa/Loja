package com.loja.loja.Repositories;

import com.loja.loja.DTOs.WalletsDTO;
import com.loja.loja.Enum.AccountType;
import com.loja.loja.Models.User;
import com.loja.loja.Models.Wallet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class WalletRepositoryImpl implements WalletRepositoryCustom{
    @Autowired
    EntityManager entityManager;

    @Override
    public List<BigDecimal> findTotalBalance() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);

        Root<Wallet> root = criteriaQuery.from(Wallet.class);

        Expression<BigDecimal> totalBalance = criteriaBuilder.sum(root.get("balance"));
        criteriaQuery.select(totalBalance);

        criteriaQuery.groupBy(root.get("accountType"));

        TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(criteriaQuery);
        List<BigDecimal> balance = typedQuery.getResultList();
        return balance;
    }

    @Override
    public List<WalletsDTO> findAllWallet(String firstName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WalletsDTO> criteriaQuery = criteriaBuilder.createQuery(WalletsDTO.class);
        Root<Wallet> root = criteriaQuery.from(Wallet.class);

        Join<Wallet, User> joinWallet = root.join("userId");

        criteriaQuery.multiselect(joinWallet.get("firstName"),
                joinWallet.get("lastName"),
                root.get("balance"),
                root.get("accountType"),
                root.get("coin"),
                root.get("createdAt"),
                root.get("updatedAt")
        );

        String pattern = "%" + firstName + "%";
        criteriaQuery.where(criteriaBuilder.like(joinWallet.get("firstName"), pattern));

        TypedQuery<WalletsDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<WalletsDTO> walletList = typedQuery.getResultList();
        return walletList;
    }

    @Override
    public List<WalletsDTO> findTop10ByBalance() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WalletsDTO> criteriaQuery = criteriaBuilder.createQuery(WalletsDTO.class);
        Root<Wallet> root = criteriaQuery.from(Wallet.class);

        Join<Wallet, User> joinWallet = root.join("userId");

        criteriaQuery.multiselect(joinWallet.get("firstName"),
                joinWallet.get("lastName"),
                root.get("balance"),
                root.get("accountType"),
                root.get("coin"),
                root.get("createdAt"),
                root.get("updatedAt")
        );

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("balance")));

        TypedQuery<WalletsDTO> typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult(0)
                .setMaxResults(10);
        List<WalletsDTO> walletList = typedQuery.getResultList();
        return walletList;
    }

    @Override
    public List<WalletsDTO> findTop5ByAccountType(AccountType accountType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WalletsDTO> criteriaQuery = criteriaBuilder.createQuery(WalletsDTO.class);
        Root<Wallet> root = criteriaQuery.from(Wallet.class);

        Join<Wallet, User> joinWallet = root.join("userId");

        criteriaQuery.multiselect(joinWallet.get("firstName"),
                joinWallet.get("lastName"),
                root.get("balance"),
                root.get("accountType"),
                root.get("coin"),
                root.get("createdAt"),
                root.get("updatedAt")
        );

        criteriaQuery.where(criteriaBuilder.equal(root.get("accountType"), accountType));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("balance")));

        TypedQuery<WalletsDTO> typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult(0)
                .setMaxResults(5);
        List<WalletsDTO> walletList = typedQuery.getResultList();
        return walletList;
    }

    @Override
    public Optional<Wallet> findWalletByUserIdAndAccountType(Long userId, AccountType accountType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Wallet> criteriaQuery = criteriaBuilder.createQuery(Wallet.class);
        Root<Wallet> root = criteriaQuery.from(Wallet.class);

        criteriaQuery.multiselect(root.get("id"),
                root.get("balance"),
                root.get("accountType"),
                root.get("createdAt"),
                root.get("updatedAt"),
                root.get("coin"),
                root.get("userId")
        );

        Predicate userIdPredicate = criteriaBuilder.equal(root.get("userId").get("id"), userId);
        Predicate accountTypePredicate = criteriaBuilder.equal(root.get("accountType"), accountType);
        criteriaQuery.where(criteriaBuilder.and(userIdPredicate, accountTypePredicate));

        TypedQuery<Wallet> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            return java.util.Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return java.util.Optional.empty();
        }
    }
}
