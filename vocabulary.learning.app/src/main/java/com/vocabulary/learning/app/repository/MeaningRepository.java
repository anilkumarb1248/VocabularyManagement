package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.MeaningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeaningRepository extends JpaRepository<MeaningEntity, Integer> {
}
