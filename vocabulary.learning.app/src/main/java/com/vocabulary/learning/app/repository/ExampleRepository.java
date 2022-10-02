package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleEntity, Integer> {
}
