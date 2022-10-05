package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.VerbEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerbRepository extends JpaRepository<VerbEntity, Integer> {
    Page<VerbEntity> findByBaseFormLike(String baseForm, Pageable pageable);
    Page<VerbEntity> findByBaseFormLikeOrPastTenseFormLikeOrPastParticipleFormLike(String baseForm, String pastTenseForm, String pastParticipleForm, Pageable pageable);
}
