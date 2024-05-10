package com.loja.loja.DTOs;

import com.loja.loja.Enum.Availability;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRegisterDTO {
    private String productName;
    private String productBrand;
    private String description;
    private BigDecimal price;
    private String category;
    private Availability availability;
    private Integer quantity;
}
