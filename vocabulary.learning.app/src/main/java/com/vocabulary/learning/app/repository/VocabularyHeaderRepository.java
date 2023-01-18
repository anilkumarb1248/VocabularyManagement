package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.VocabularyHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyHeaderRepository extends JpaRepository<VocabularyHeaderEntity, Integer> {

    @Query("SELECT v FROM VocabularyHeaderEntity v WHERE v.parentId IS NULL")
    List<VocabularyHeaderEntity> findAllParentIdIsNull();
    List<VocabularyHeaderEntity> findByParentId(Integer parentId);

    @Modifying
    void deleteByParentId(Integer parentId);
}
