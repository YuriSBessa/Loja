package com.loja.loja.Repositories;

import com.loja.loja.Models.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllProducts(String availability);
    Product findProductsById(Long id);
}
