package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.VerbExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerbExampleRepository extends JpaRepository<VerbExampleEntity, Integer> {

    @Modifying
    @Query(value = "DELETE FROM VERB_EXAMPLE WHERE VERB_ID= :verbId", nativeQuery = true)
    public void deleteByVerbId(Integer verbId);
}
