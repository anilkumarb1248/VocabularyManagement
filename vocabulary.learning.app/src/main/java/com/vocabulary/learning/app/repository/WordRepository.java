package com.vocabulary.learning.app.repository;

import com.vocabulary.learning.app.entity.WordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Integer> {
    Page<WordEntity> findByWordLike(String s, Pageable pageable);

    Page<WordEntity> findByWordIgnoreCaseLike(String s, Pageable pageable);

    Optional<WordEntity> findByWord(String searchWord);

    @Modifying
    @Query(value = "DELETE FROM WORD WHERE WORD = :searchWord", nativeQuery = true)
    void deleteByWord(String searchWord);
}
