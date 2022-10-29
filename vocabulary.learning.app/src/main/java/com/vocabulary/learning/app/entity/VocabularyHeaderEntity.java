package com.vocabulary.learning.app.entity;

import com.vocabulary.learning.app.enums.VocabularyType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VOCABULARY_HEADER")
public class VocabularyHeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="VOCABULARY_HEADER_ID", unique = true)
    private Integer vocabularyHeaderId;

    @Column(name="VOCABULARY_HEADER")
    private String vocabularyHeader;

    @Column(name="VOCABULARY_HEADER_PARENT")
    private Integer vocabularyHeaderParent;

    @Column(name="VOCABULARY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private VocabularyType vocabularyType;

    public Integer getVocabularyHeaderId() {
        return vocabularyHeaderId;
    }

    public void setVocabularyHeaderId(Integer vocabularyHeaderId) {
        this.vocabularyHeaderId = vocabularyHeaderId;
    }

    public String getVocabularyHeader() {
        return vocabularyHeader;
    }

    public void setVocabularyHeader(String vocabularyHeader) {
        this.vocabularyHeader = vocabularyHeader;
    }

    public Integer getVocabularyHeaderParent() {
        return vocabularyHeaderParent;
    }

    public void setVocabularyHeaderParent(Integer vocabularyHeaderParent) {
        this.vocabularyHeaderParent = vocabularyHeaderParent;
    }

    public VocabularyType getVocabularyType() {
        return vocabularyType;
    }

    public void setVocabularyType(VocabularyType vocabularyType) {
        this.vocabularyType = vocabularyType;
    }
}
