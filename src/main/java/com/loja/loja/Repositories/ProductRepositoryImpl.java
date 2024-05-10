package com.loja.loja.Repositories;

import com.loja.loja.Models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom{
    @Autowired
    EntityManager entityManager;
    @Override
    public List<Product> findAllProducts(String availability) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.multiselect(
                root.get("productName"),
                root.get("productBrand"),
                root.get("description"),
                root.get("price"),
                root.get("category"),
                root.get("availability"),
                root.get("quantity")
        );

        if(availability != null){
            criteriaQuery.where(criteriaBuilder.equal(root.get("availability"), availability));
        }

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> products = typedQuery.getResultList();
        return products;
    }
    @Override
    public Product findProductsById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.multiselect(
                root.get("productName"),
                root.get("productBrand"),
                root.get("description"),
                root.get("price"),
                root.get("category"),
                root.get("availability"),
                root.get("quantity")
        );

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        Product product = typedQuery.getSingleResult();
        return product;
    }
}
