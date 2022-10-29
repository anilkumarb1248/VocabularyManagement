package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.VocabularyHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabularyHeaderRepository extends JpaRepository<VocabularyHeaderEntity, Integer> {
}
