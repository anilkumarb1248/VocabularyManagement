package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.AllMeaningsEntity;
import com.vocabulary.learning.app.enums.MeaningType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllMeaningsRepository extends JpaRepository<AllMeaningsEntity, Integer> {

    Page<AllMeaningsEntity> findByMeaningType(MeaningType meaningType, Pageable pageable);
}
