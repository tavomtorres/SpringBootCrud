package com.example.challengecrud.mapper;

import com.example.challengecrud.dto.ImageDTO;
import com.example.challengecrud.model.Image;
import com.example.challengecrud.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ImageDTO convertToDto(Image param){
        ImageDTO dto = modelMapper.map(param,ImageDTO.class);
        return dto;
    }

    public Image convertToEntity(ImageDTO dto){

        Image entity = modelMapper.map(dto,Image.class);
        return entity;
    }

    public List<ImageDTO> convertListEntityToDto(List<Image> entityList){
        return entityList.stream()
                .map(entity -> convertToDto(entity))
                .collect(Collectors.toList());
    }

    public List<Image> convertListDtoToEntity(List<ImageDTO> dtoList){
        return dtoList.stream()
                .map(entity -> convertToEntity(entity))
                .collect(Collectors.toList());
    }
}
