package com.loja.loja.Controllers;

import com.loja.loja.DTOs.OrderDTO;
import com.loja.loja.Enum.AccountType;
import com.loja.loja.Enum.OrderStatus;
import com.loja.loja.Models.Order;
import com.loja.loja.MyExceptions.ResourceNotFoundException;
import com.loja.loja.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService service;

    @PostMapping("/create-order")
    public ResponseEntity<Object> createNewOrder(
            @RequestParam(required = true, value = "userId", defaultValue = "") Long userId,
            @RequestParam(required = true, value = "productId", defaultValue = "") Long productId,
            @RequestParam(required = true, value = "quantity", defaultValue = "") int quantity,
            @RequestParam(required = true, value = "status", defaultValue = "") OrderStatus status,
            @RequestParam(required = true, value = "AccountType", defaultValue = "") AccountType accountType
    ) throws ResourceNotFoundException {
        return service.createOrder(userId, productId, quantity, status, accountType);
    }
    @GetMapping("/find-by-user-and-product")
    public ResponseEntity<List<OrderDTO>> findByUserAndProductName(
            @RequestParam(required = true, value = "firstName", defaultValue = "") String firstName,
            @RequestParam(required = true, value = "productName", defaultValue = "") String productName
    ){
        return new ResponseEntity<>(service.getByUserAndProductName(firstName, productName), HttpStatus.OK);
    }

    @GetMapping("/find-by-user-and-date")
    public ResponseEntity<List<OrderDTO>> findByUserAndDate(
            @RequestParam(required = true, value = "firstName", defaultValue = "") String firstName,

            @RequestParam(required = true, value = "dateInitial", defaultValue = "")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateInitial,

            @RequestParam(required = true, value = "dateFinal", defaultValue = "")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFinal
    ) {
        return new ResponseEntity<>(service.getByUserAndDate(firstName, dateInitial, dateFinal), HttpStatus.OK);
    }
}
