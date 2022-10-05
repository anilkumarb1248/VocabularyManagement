package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

    Optional<ImageEntity> findByName(String name);
    Optional<ImageEntity> findByVerbId(Integer verbId);

    @Modifying
    @Query(value = "DELETE FROM IMAGE WHERE VERB_ID= :verbId", nativeQuery = true)
    public void deleteByVerbId(Integer verbId);

}
