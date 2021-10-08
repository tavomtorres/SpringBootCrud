package com.example.challengecrud.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image extends BaseEntity {

    @Column(name = "URL")
    private String url;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_PRODUCT",nullable = false)
    private Product product;

}
