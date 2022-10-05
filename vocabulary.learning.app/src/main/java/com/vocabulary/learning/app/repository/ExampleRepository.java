package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleEntity, Integer> {

    @Modifying
    @Query(value = "DELETE FROM EXAMPLE WHERE VERB_ID= :verbId", nativeQuery = true)
    public void deleteByVerbId(Integer verbId);
}
