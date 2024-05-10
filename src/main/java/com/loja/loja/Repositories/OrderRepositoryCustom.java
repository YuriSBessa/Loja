package com.loja.loja.Repositories;

import com.loja.loja.DTOs.OrderDTO;

import java.util.Date;
import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderDTO> findByUserAndProductName(String firstName, String productName);

    List<OrderDTO> findByUserAndDate(String firstName, Date dateInitial, Date dateFinal);
}
