package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.MeaningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeaningRepository extends JpaRepository<MeaningEntity, Integer> {
    @Modifying
    @Query (value = "DELETE FROM MEANING WHERE VERB_ID= :verbId", nativeQuery = true)
    public void deleteByVerbId(Integer verbId);
}
