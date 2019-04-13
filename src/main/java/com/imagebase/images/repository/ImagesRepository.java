package com.imagebase.images.repository;


import com.imagebase.images.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long> {

}
