package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

    Optional<ImageEntity> findByName(String name);

}
