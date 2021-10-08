package com.example.challengecrud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Data
public class UpdateProductDTO {

    @Size(min = 3, max = 50,message = "Name minimo 3 y maximo 50 caracteres")
    @NotEmpty(message = "name no puede estar vacio")
    @JsonProperty("name")
    private String name;

    @Size(min = 3, max = 50,message = "Brand minimo 3 y maximo 50 caracteres")
    @NotEmpty(message = "brand no puede estar vacio")
    @JsonProperty("brand")
    private String brand;

    @JsonProperty("size")
    private String size;

    @NotEmpty(message = "price no puede estar vacio")
    @JsonProperty("price")
    private String price;

    @URL(protocol = "https",message = "formato incorrecto de la url")
    @NotEmpty(message = "principalImage no puede estar vacio")
    @JsonProperty("principalImage")
    private String principalImage;

    @JsonProperty("otherImages")
    private List<ImageDTO> secondImages;
}
