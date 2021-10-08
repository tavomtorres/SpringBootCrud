package com.example.challengecrud.controller;

import com.example.challengecrud.dto.ProductDTO;
import com.example.challengecrud.exception.ApiRequestException;
import com.example.challengecrud.model.Product;
import com.example.challengecrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> list = productService.getAllProduct();
        return new ResponseEntity<List<ProductDTO>>(list,HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable long id){
        ProductDTO productDTO = productService.getProductById(id);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
    }
    @PostMapping("/product")
    ResponseEntity<ProductDTO> addProduct(@Validated @RequestBody ProductDTO newP){
        ProductDTO newProductDTO = productService.addNewProduct(newP);
        return new ResponseEntity<ProductDTO>(newProductDTO,HttpStatus.CREATED);
    }


}
