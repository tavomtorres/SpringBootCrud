package com.example.challengecrud.service;

import com.example.challengecrud.dto.ProductDTO;


import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProduct();
    ProductDTO getProductById(long productId);
    public ProductDTO addNewProduct(ProductDTO newProductDTO);
}
