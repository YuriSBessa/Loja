package com.loja.loja.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private String firstName;
    private String lastName;
    private String state;
    private String city;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal totalValue;
}
