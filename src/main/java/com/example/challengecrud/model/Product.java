package com.example.challengecrud.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends BaseEntity{

    @NaturalId
    @Column(name = "SKU", nullable = false, unique = true)
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Image> secondImages;

}
