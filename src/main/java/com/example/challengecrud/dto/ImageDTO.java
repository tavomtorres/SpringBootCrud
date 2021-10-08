package com.example.challengecrud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class ImageDTO {

    @URL(protocol = "https",message = "formato incorrecto de la url")
    @JsonProperty("url")
    private String url;

}
