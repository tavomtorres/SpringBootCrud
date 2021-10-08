package com.example.challengecrud.repository;

import com.example.challengecrud.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {

}
