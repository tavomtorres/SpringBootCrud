package com.example.challengecrud.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends BaseEntity{

    @Column(name = "SKU")
    private String sku;

    @Column(name = "Name")
    private String name;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Size")
    private String size;

    @Column(name = "Price")
    private Float price;

    @Column(name = "Principal_Image")
    private String principalImage;

    @OneToMany(mappedBy = "product")
    private List<Image> secondImages;

}
