package com.loja.loja.Models;

import com.loja.loja.Enum.Availability;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
    @SequenceGenerator(name = "product_seq_generator", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_brand")
    private String productBrand;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category")
    private String category;

    @Column(name = "available")
    @Enumerated(EnumType.STRING)
    private Availability availability;

    @Column(name = "quantity")
    private Integer quantity;

    public Product(String productName, String productBrand, String description, BigDecimal price, String category, Availability availability, int quantity) {
        this.productName = productName;
        this.productBrand = productBrand;
        this.description = description;
        this.price = price;
        this.category = category;
        this.availability = availability;
        this.quantity = quantity;
    }

    public Product(){

    }
}
