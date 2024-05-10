package com.loja.loja.DTOs;

import com.loja.loja.Enum.Availability;
import com.loja.loja.Models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductsDTO {
    private String productName;
    private String productBrand;
    private String description;
    private BigDecimal price;
    private String category;
    private String availability;
    private int quantity;

    public ProductsDTO(Product product) {
        this.productName = product.getProductName();
        this.productBrand = product.getProductBrand();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();

        var available = product.getAvailability();
        if(available != null){
            if(Availability.AVAILABLE.equals(available)){
                this.availability = "Disponível";
            } else if(Availability.SOLD_OUT.equals(available)){
                this.availability = "Esgotado";
            } else if(Availability.LACKING.equals(available)){
                this.availability = "Em Falta";
            }
        } else{
            this.availability = "-";
        }

        this.quantity = product.getQuantity();
    }
}
