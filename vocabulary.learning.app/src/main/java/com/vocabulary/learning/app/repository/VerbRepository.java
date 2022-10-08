package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.VerbEntity;
import com.vocabulary.learning.app.enums.LearningStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerbRepository extends JpaRepository<VerbEntity, Integer> {
    Page<VerbEntity> findByBaseFormLike(String baseForm, Pageable pageable);
    Page<VerbEntity> findByBaseFormLikeOrPastTenseFormLikeOrPastParticipleFormLike(String baseForm, String pastTenseForm, String pastParticipleForm, Pageable pageable);

    List<VerbEntity> findAllByLearningStatus(LearningStatus status);

    List<VerbEntity> findAllByVerbIdIn(List<Integer> verbIds, Sort sort);

    Optional<VerbEntity> findByBaseForm(String baseForm);

}
