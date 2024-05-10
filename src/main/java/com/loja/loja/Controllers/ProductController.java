package com.loja.loja.Controllers;

import com.loja.loja.DTOs.ProductsDTO;
import com.loja.loja.Enum.Availability;
import com.loja.loja.Models.Product;
import com.loja.loja.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping("/register-product")
    public ResponseEntity<Product> createProduct(
            @RequestParam(required = true, value = "productName", defaultValue = "") String productName,
            @RequestParam(required = true, value = "productBrand", defaultValue = "") String productBrand,
            @RequestParam(required = true, value = "description", defaultValue = "") String description,
            @RequestParam(required = true, value = "price", defaultValue = "") BigDecimal price,
            @RequestParam(required = true, value = "category", defaultValue = "") String category,
            @RequestParam(required = true, value = "availability", defaultValue = "") Availability availability,
            @RequestParam(required = true, value = "quantity", defaultValue = "") int quantity
    ){
        return service.newRegisteredProduct(productName, productBrand, description, price, category, availability, quantity);
    }
    @GetMapping("/find-all")
    public ResponseEntity<List<ProductsDTO>> findAllProducts(
            @RequestParam(required = false, value = "availability", defaultValue = "") String availability
    ){
        return new ResponseEntity<>(service.findAll(availability), HttpStatus.OK);
    }
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ProductsDTO> findProductsById(
            @PathVariable(required = true, value = "id") Long id
    ){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }
    @PutMapping("/edit-product/{id}")
    public ResponseEntity<Object> editProduct(
            @RequestParam(required = false, value = "productName") String productName,
            @RequestParam(required = false, value = "productBrand") String productBrand,
            @RequestParam(required = false, value = "description") String description,
            @RequestParam(required = false, value = "price") BigDecimal price,
            @RequestParam(required = false, value = "category") String category,
            @RequestParam(required = false, value = "availability") Availability availability,
            @RequestParam(required = false, value = "quantity") Integer quantity,

            @PathVariable(required = true, value = "id") Long id
    ){
        return service.editProducts(productName, productBrand, description, price, category, availability, quantity, id);
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Object> deleteProdut(
            @PathVariable(required = true, value = "id") Long id
    ){
        return service.deleteProducts(id);
    }
}
