package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.WordMeaningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordMeaningRepository extends JpaRepository<WordMeaningEntity, Integer> {
}
