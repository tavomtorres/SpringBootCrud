package com.example.challengecrud.controller;

import com.example.challengecrud.dto.ProductDTO;
import com.example.challengecrud.dto.UpdateProductDTO;
import com.example.challengecrud.model.Product;
import com.example.challengecrud.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> list = productService.getAllProduct();
        return new ResponseEntity<List<ProductDTO>>(list,HttpStatus.OK);
    }
    @GetMapping("/products/{id}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable long id){
        ProductDTO productDTO = productService.getProductById(id);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
    }
    @PostMapping("/products")
    ResponseEntity<ProductDTO> addProduct(@Validated @RequestBody ProductDTO newP){
        ProductDTO newProductDTO = productService.addNewProduct(newP);
        return new ResponseEntity<ProductDTO>(newProductDTO,HttpStatus.CREATED);
    }
    @PutMapping("/products/{SKU}")
    ResponseEntity<ProductDTO> updateProduct(@Validated @RequestBody UpdateProductDTO updatedP, @PathVariable("SKU") String sku){
        ProductDTO updatedProductDTO = productService.updateProduct(updatedP,sku);
        return new ResponseEntity<ProductDTO>(updatedProductDTO,HttpStatus.OK);
    }
    @PatchMapping("/products/{SKU}")
    ResponseEntity<ProductDTO> partialUpdateProduct(@PathVariable("SKU") String sku, @RequestBody Map<Object, Object> fields){
        ProductDTO productDto  = productService.partialUpdate(sku,fields);
        return new ResponseEntity<ProductDTO>(productDto,HttpStatus.OK);
    }
    @DeleteMapping("products/{SKU}")
    public HttpStatus deleteProduct(@PathVariable("SKU") String sku){
        return productService.deleteProduct(sku);
    }


}
