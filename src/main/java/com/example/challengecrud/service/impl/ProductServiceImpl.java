package com.example.challengecrud.service.impl;

import com.example.challengecrud.dto.ImageDTO;
import com.example.challengecrud.dto.ProductDTO;
import com.example.challengecrud.dto.UpdateProductDTO;
import com.example.challengecrud.exception.ApiAlreadyExistException;
import com.example.challengecrud.exception.ApiNotFoundException;
import com.example.challengecrud.exception.ApiRequestException;
import com.example.challengecrud.mapper.ImageMapper;
import com.example.challengecrud.mapper.ProductMapper;
import com.example.challengecrud.model.Image;
import com.example.challengecrud.model.Product;
import com.example.challengecrud.model.SkuValues;
import com.example.challengecrud.repository.ImageRepository;
import com.example.challengecrud.repository.ProductRepository;
import com.example.challengecrud.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ProductMapper productMapper;
    private final ImageMapper imageMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              ImageRepository imageRepository,
                              ImageMapper imageMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
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
    public ProductDTO getProductBySku(String sku) {
        skuValidation(sku);
        Optional<Product> p = productRepository.findBySku(sku);
        if(p.isPresent()){
            Product product = p.get();
            return productMapper.convertToDto(product);
        }else{
            throw new ApiNotFoundException("SKU no encontrado");
        }

    }

    @Override
    @Transactional
    public ProductDTO addNewProduct(ProductDTO newProductDTO) {
        //*Validar SKU
        skuValidation(newProductDTO.getSku());
        priceValidation(newProductDTO.getPrice());

        Product newProduct = productMapper.convertToEntity(newProductDTO);
        try {
            saveImagesBySecondImages(newProductDTO.getSecondImages(),newProduct);
            this.productRepository.save(newProduct);
            //agrega doble a la bd, bug, revisar.
        }catch (Exception e){
            //se debe ser mas especifico en la excepcion, ya que el price si lo pongo en letras, me sale este error
            throw new ApiAlreadyExistException("El SKU ya Existe");
        }

        return newProductDTO;
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(UpdateProductDTO updateProductDTO, String sku) {

        skuValidation(sku);
        Optional<Product> optionalProduct = productRepository.findBySku(sku);

        if(optionalProduct.isPresent()){
            Product oldProduct = optionalProduct.get();
            oldProduct.setName(updateProductDTO.getName());
            oldProduct.setBrand(updateProductDTO.getBrand());
            oldProduct.setSize(updateProductDTO.getSize());
            oldProduct.setPrice( Float.parseFloat(updateProductDTO.getPrice()));
            oldProduct.setPrincipalImage(updateProductDTO.getPrincipalImage());
            //oldProduct.setSecondImages(imageMapper.convertListDtoToEntity(updateProductDTO.getSecondImages(),oldProduct));
            productRepository.save(oldProduct);

            return productMapper.convertToDto(oldProduct);
        }else{
            throw new ApiRequestException("El SKU no existe");
        }


    }

    @Override
    public HttpStatus deleteProduct(String sku) {
        skuValidation(sku);
        Optional<Product> optionalProduct = productRepository.findBySku(sku);

        if(optionalProduct.isPresent()){
            productRepository.deleteById(optionalProduct.get().getId());
            return HttpStatus.OK;
        }else{
            return HttpStatus.NOT_FOUND;
        }
    }

    @Override
    public ProductDTO partialUpdate(String sku, Map<Object, Object> fields) {
        skuValidation(sku);
        Optional<Product> optionalProduct = productRepository.findBySku(sku);
        if(optionalProduct.isPresent()){
            Product productEntity = optionalProduct.get();
            fields.forEach((k,v)->{
                Field field = ReflectionUtils.findField(Product.class,(String) k);
                field.setAccessible(true);
                ReflectionUtils.setField(field,productEntity,v);
            });
            try {
                productRepository.save(productEntity);
                return productMapper.convertToDto(productEntity);
            }catch(Exception e){
                throw new ApiRequestException("Hubo un error al guardar el cambio, contacte al administrador");
            }
        }else{
            throw new ApiNotFoundException("No se ha encontrado el SKU");
        }
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
    private void priceValidation(String price){
        float number;
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
