package com.loja.loja.Repositories;

import com.loja.loja.DTOs.OrderDTO;
import com.loja.loja.Models.Order;
import com.loja.loja.Models.Product;
import com.loja.loja.Models.User;
import com.loja.loja.Models.Wallet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    @Autowired
    EntityManager entityManager;
    @Override
    public List<OrderDTO> findByUserAndProductName(String firstName, String productName){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderDTO> criteriaQuery = criteriaBuilder.createQuery(OrderDTO.class);

        Root<Order> root = criteriaQuery.from(Order.class);

        Join<Order, User> joinUser = root.join("userId");
        Join<Order, Product> joinProduct = root.join("productId");

        criteriaQuery.multiselect(joinUser.get("firstName"),
                                  joinUser.get("lastName"),
                                  joinUser.get("state"),
                                  joinUser.get("city"),
                                  joinProduct.get("productName"),
                                  joinProduct.get("productBrand"),
                                  root.get("quantity"),
                                  root.get("totalValue")
        );

        Predicate userFirstNamePredicate = criteriaBuilder.equal(joinUser.get("firstName"), firstName);
        Predicate productNamePredicate = criteriaBuilder.equal(joinProduct.get("productName"), productName);
        criteriaQuery.where(criteriaBuilder.and(userFirstNamePredicate), productNamePredicate);

        TypedQuery<OrderDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<OrderDTO> orders = typedQuery.getResultList();
        return orders;
    }

    @Override
    public List<OrderDTO> findByUserAndDate(String firstName, Date dateInitial, Date dateFinal){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderDTO> criteriaQuery = criteriaBuilder.createQuery(OrderDTO.class);

        Root<Order> root = criteriaQuery.from(Order.class);

        Join<Order, User> userJoin = root.join("userId");
        Join<Order, Product> productJoin = root.join("productId");

        criteriaQuery.multiselect(userJoin.get("firstName"),
                                  userJoin.get("lastName"),
                                  userJoin.get("state"),
                                  userJoin.get("city"),
                                  productJoin.get("productName"),
                                  productJoin.get("productBrand"),
                                  root.get("quantity"),
                                  root.get("totalValue")
        );

        Predicate userFirstNamePredicate = criteriaBuilder.equal(userJoin.get("firstName"), firstName);
        Predicate datePredicate = criteriaBuilder.between(root.get("orderDate"), dateInitial, dateFinal);
        criteriaQuery.where(criteriaBuilder.and(userFirstNamePredicate, datePredicate));

        TypedQuery<OrderDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
