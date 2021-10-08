package com.example.challengecrud.service.impl;

import com.example.challengecrud.dto.ImageDTO;
import com.example.challengecrud.dto.ProductDTO;
import com.example.challengecrud.exception.ApiAlreadyExistException;
import com.example.challengecrud.exception.ApiNotFoundException;
import com.example.challengecrud.exception.ApiRequestException;
import com.example.challengecrud.mapper.ProductMapper;
import com.example.challengecrud.model.Image;
import com.example.challengecrud.model.Product;
import com.example.challengecrud.model.SkuValues;
import com.example.challengecrud.repository.ImageRepository;
import com.example.challengecrud.repository.ProductRepository;
import com.example.challengecrud.service.ProductService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> entityList = productRepository.findAll();
        if(entityList.isEmpty())
            throw new ApiNotFoundException("Opps!, no se encontraron datos");

        return productMapper.convertListEntityToDto(entityList);
    }

    @Override
    public ProductDTO getProductById(long productId) {
        try {
            Product product = productRepository.getById(productId);
            return productMapper.convertToDto(product);
        }catch (Exception e){
            throw new ApiNotFoundException("Opps!, el producto con el id: " +productId + " no se ha encontrado");
        }

    }

    @Override
    @Transactional
    public ProductDTO addNewProduct(ProductDTO newProductDTO) {
        //*Validar SKU
        skuValidation(newProductDTO.getSku());
        Pricevalidation(newProductDTO.getPrice());

        Product newProduct = productMapper.convertToEntity(newProductDTO);
        try {
            saveImagesBySecondImages(newProductDTO.getSecondImages(),newProduct);
            this.productRepository.save(newProduct);
        }catch (Exception e){
            //se debe ser mas especifico en la excepcion, ya que el price si lo pongo en letras, me sale este error
            throw new ApiAlreadyExistException("El SKU ya Existe");
        }

        return newProductDTO;
    }

    //Guarda las imagenes secundarias del POST de producto
    private void saveImagesBySecondImages(List<ImageDTO> listImageDto, Product newProduct){
        if(!listImageDto.isEmpty()) {
            for (ImageDTO a : listImageDto) {
                Image image = new Image();
                image.setUrl(a.getUrl());
                image.setProduct(newProduct);
                imageRepository.save(image);
            }
        }
    }
    private void Pricevalidation(String price){
        Float number;
        try {
             number = Float.parseFloat(price);
        }catch (Exception e){
            throw new ApiRequestException("EL formato de precio no es valido");
        }

            if(number < 1.0){
                throw new ApiRequestException("El precio minimo 1.0 ");
            }
            if(number > 99999999.0){
                throw new ApiRequestException("El precio debe ser menor a 99999999.00 ");
            }

    }
    private void skuValidation(String sku){

        //valido longitud entre 11 y 12
        if(sku.length() < 11 || sku.length() > 12){
            throw new ApiRequestException("El SKU no cumple el rango, MIN: FAL-1000000, Max: FAL-99999999");
        }

        //Valido si tiene -
        if(!sku.contains("-")){
            throw new ApiRequestException("El SKU debe contener un '-', Formato: FAL-XXXXXXX ");
        }
        //Letras
        String[] array = sku.split("-",2);

        if(array[0].length() != 3){
            throw new ApiRequestException("El SKU debe comenzar con 3 letras 'FAL' seguido de '-' ");
        }else{
            if(!array[0].equals(SkuValues.FAL.name())){
                throw new ApiRequestException("Las 3 primeras letras del SKU deben ser = 'FAL'");
            }
        }
        try {
            int number = Integer.parseInt(array[1]);
        }catch(Exception e){
            throw new ApiRequestException("despues del '-', solo numeros");
        }

    }
}
