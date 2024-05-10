package com.loja.loja.DTOs;

import com.loja.loja.Enum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrdersDTO {
    private Long userId;
    private Long productId;
    private int quantity;
    private BigDecimal totalValue;
    private OrderStatus status;
    private Date orderDate;
    private Date expectedDeliveryDate;
}
