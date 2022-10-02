package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.VerbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerbRepository extends JpaRepository<VerbEntity, Integer> {
}
