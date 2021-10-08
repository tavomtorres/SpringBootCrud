package com.example.challengecrud.service;

import com.example.challengecrud.dto.ProductDTO;
import com.example.challengecrud.dto.UpdateProductDTO;
import org.springframework.http.HttpStatus;


import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProduct();
    ProductDTO getProductById(long productId);
    ProductDTO getProductBySku(String sku);
    ProductDTO addNewProduct(ProductDTO newProductDTO);
    ProductDTO updateProduct(UpdateProductDTO updateProductDTO, String sku);
    HttpStatus deleteProduct(String sku);
}
