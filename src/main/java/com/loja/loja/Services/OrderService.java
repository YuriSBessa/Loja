package com.loja.loja.Services;

import com.loja.loja.DTOs.CreateOrdersDTO;
import com.loja.loja.DTOs.OrderDTO;
import com.loja.loja.Enum.AccountType;
import com.loja.loja.Enum.Availability;
import com.loja.loja.Enum.OrderStatus;
import com.loja.loja.Models.Order;
import com.loja.loja.Models.Product;
import com.loja.loja.Models.Wallet;
import com.loja.loja.MyExceptions.ResourceNotFoundException;
import com.loja.loja.Repositories.OrderRepository;
import com.loja.loja.Repositories.ProductRepository;
import com.loja.loja.Repositories.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository repository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WalletRepository walletRepository;

    public ResponseEntity<Object> createOrder(Long userId,
                                              Long productId,
                                              int quantity,
                                              OrderStatus status,
                                              AccountType accountType) throws ResourceNotFoundException {
        Product product = getProduct(productId);
        Wallet wallet = getWallet(userId, accountType);

        validateProductAvailability(product, quantity);
        validateWalletBalance(wallet, product.getPrice(), quantity);

        CreateOrdersDTO ordersDTO = createOrderEntity(userId, productId, quantity, status, product.getPrice());
        updateProductAvailability(product, quantity);
        updateWalletBalance(wallet, product.getPrice(), quantity);

        Order order = modelMapper.map(ordersDTO, Order.class);

        repository.save(order);
        productRepository.save(product);
        walletRepository.save(wallet);

        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido feito com sucesso");
    }

    private CreateOrdersDTO createOrderEntity(Long userId,
                                  Long productId,
                                  int quantity,
                                  OrderStatus status,
                                  BigDecimal productPrice){
        CreateOrdersDTO ordersDTO = new CreateOrdersDTO();

        Date orderDate = new Date();
        Date expectedDeliveryDate = new Date(orderDate.getYear(), orderDate.getMonth(), 20);
        BigDecimal totalValue = productPrice.multiply(BigDecimal.valueOf(quantity));

        ordersDTO.setUserId(userId);
        ordersDTO.setProductId(productId);
        ordersDTO.setQuantity(quantity);
        ordersDTO.setStatus(status);
        ordersDTO.setOrderDate(orderDate);
        ordersDTO.setTotalValue(totalValue);
        ordersDTO.setExpectedDeliveryDate(expectedDeliveryDate);

        return ordersDTO;
    }

    private Product getProduct(Long productId) throws ResourceNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    private Wallet getWallet(Long userId, AccountType accountType) throws ResourceNotFoundException {
        return walletRepository.findWalletByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira não encontrada"));
    }

    private void validateProductAvailability(Product product, int quantity){
        if(quantity > product.getQuantity()){
            throw new IllegalArgumentException("Quantidade insuficiente de produtos disponíveis");
        }
    }
    private void validateWalletBalance(Wallet wallet, BigDecimal productPrice, int quantity){
        BigDecimal totalValue = productPrice.multiply(BigDecimal.valueOf(quantity));
        if (wallet.getBalance().compareTo(totalValue) < 0) {
            throw new IllegalArgumentException("Saldo da carteira insuficiente");
        }
    }

    private void updateProductAvailability(Product product, int quantity){
        int remainingQuantity = product.getQuantity() - quantity;
        product.setQuantity(remainingQuantity);
        if(remainingQuantity == 0){
            product.setAvailability(Availability.SOLD_OUT);
        }
    }

    private void updateWalletBalance(Wallet wallet, BigDecimal productPrice, int quantity) {
        BigDecimal totalValue = productPrice.multiply(BigDecimal.valueOf(quantity));
        wallet.setBalance(wallet.getBalance().subtract(totalValue));
    }

    public List<OrderDTO> getByUserAndProductName(String firstName,
                                                  String productName) {
        var result = repository.findByUserAndProductName(firstName, productName);
        return result;
    }

    public List<OrderDTO> getByUserAndDate(String firstName, Date dateInitial, Date dateFinal) {
        var result = repository.findByUserAndDate(firstName, dateInitial, dateFinal);
        return result;
    }
}
