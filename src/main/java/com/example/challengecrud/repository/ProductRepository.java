package com.example.challengecrud.repository;

import com.example.challengecrud.dto.ProductDTO;
import com.example.challengecrud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

   Optional<Product> findBySku(String sku);
}
