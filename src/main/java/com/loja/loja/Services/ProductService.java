package com.loja.loja.Services;

import com.loja.loja.DTOs.ProductRegisterDTO;
import com.loja.loja.DTOs.ProductsDTO;
import com.loja.loja.Enum.Availability;
import com.loja.loja.Models.Product;
import com.loja.loja.Repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;
    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity<Product> newRegisteredProduct(String productName,
                                                        String productBrand,
                                                        String description,
                                                        BigDecimal price,
                                                        String category,
                                                        Availability availability,
                                                        int quantity
    ) {
        ProductRegisterDTO dto = new ProductRegisterDTO();

        dto.setProductName(productName);
        dto.setProductBrand(productBrand);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setCategory(category);
        dto.setAvailability(availability);
        dto.setQuantity(quantity);

        Product product = modelMapper.map(dto, Product.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }

    public List<ProductsDTO> findAll(String availability) {
        var result = repository.findAllProducts(availability);

        return result.stream().map(ProductsDTO::new)
                .toList();
    }

    public ProductsDTO findById(Long id) {
        var result = repository.findProductsById(id);

        ProductsDTO dto = new ProductsDTO();
        dto.setProductName(result.getProductName());
        dto.setProductBrand(result.getProductBrand());
        dto.setDescription(result.getDescription());
        dto.setPrice(result.getPrice());
        dto.setCategory(result.getCategory());

        var availability = result.getAvailability();

        if(availability != null){
            if(Availability.AVAILABLE.equals(availability)){
                dto.setAvailability("Disponível");
            } else if(Availability.SOLD_OUT.equals(availability)){
                dto.setAvailability("Esgotado");
            } else if (Availability.LACKING.equals(availability)) {
                dto.setAvailability("Em Falta");
            }
        } else{
            dto.setAvailability("-");
        }

        dto.setQuantity(result.getQuantity());
        return dto;
    }

    public ResponseEntity<Object> editProducts(String productName,
                                               String productBrand,
                                               String description,
                                               BigDecimal price,
                                               String category,
                                               Availability availability,
                                               Integer quantity,
                                               Long id
    ) {
        Optional<Product> productObj = repository.findById(id);
        ProductRegisterDTO dto = new ProductRegisterDTO();

        if(productObj.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        dto.setProductName(productName);
        dto.setProductBrand(productBrand);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setCategory(category);
        dto.setAvailability(availability);
        dto.setQuantity(quantity);

        Product product = productObj.get();
        modelMapper.map(dto, product);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(product));
    }

    public ResponseEntity<Object> deleteProducts(Long id) {
        Optional<Product> productObj = repository.findById(id);

        if(productObj.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        repository.delete(productObj.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso");
    }
}
