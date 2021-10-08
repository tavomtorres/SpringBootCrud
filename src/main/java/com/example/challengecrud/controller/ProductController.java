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
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> list = productService.getAllProduct();
        return new ResponseEntity<List<ProductDTO>>(list,HttpStatus.OK);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable long id){
        ProductDTO productDTO = productService.getProductById(id);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
    }

    @GetMapping("/products/sku/{SKU}")
    public ResponseEntity<ProductDTO> getProductBySku(@PathVariable("SKU") String sku){
        ProductDTO productDTO = productService.getProductBySku(sku);
        return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> addProduct(@Validated @RequestBody ProductDTO newP){
        ProductDTO newProductDTO = productService.addNewProduct(newP);
        return new ResponseEntity<ProductDTO>(newProductDTO,HttpStatus.CREATED);
    }
    @PutMapping("/products/{SKU}")
    public ResponseEntity<ProductDTO> updateProduct(@Validated @RequestBody UpdateProductDTO updatedP, @PathVariable("SKU") String sku){
        ProductDTO updatedProductDTO = productService.updateProduct(updatedP,sku);
        return new ResponseEntity<ProductDTO>(updatedProductDTO,HttpStatus.OK);
    }
    @PatchMapping("/products/{SKU}")
    public ResponseEntity<ProductDTO> partialUpdateProduct(@PathVariable("SKU") String sku, @RequestBody Map<Object, Object> fields){
        ProductDTO productDto  = productService.partialUpdate(sku,fields);
        return new ResponseEntity<ProductDTO>(productDto,HttpStatus.OK);
    }
    @DeleteMapping("products/{SKU}")
    public HttpStatus deleteProduct(@PathVariable("SKU") String sku){
        return productService.deleteProduct(sku);
    }


}
