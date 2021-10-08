package com.example.challengecrud.mapper;

import com.example.challengecrud.dto.ProductDTO;
import com.example.challengecrud.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO convertToDto(Product param){
        ProductDTO dto = modelMapper.map(param,ProductDTO.class);
        return dto;
    }

    public Product convertToEntity(ProductDTO dto){
        Product entity = modelMapper.map(dto,Product.class);
        return entity;
    }

    public List<ProductDTO> convertListEntityToDto(List<Product> entityList){
        return entityList.stream()
                .map(entity -> convertToDto(entity))
                .collect(Collectors.toList());
    }

}
