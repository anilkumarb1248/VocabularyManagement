package com.vocabulary.learning.app.model;

import com.vocabulary.learning.app.enums.VocabularyType;

public class VocabularyHeader {
    private Integer vocabularyHeaderId;
    private String vocabularyHeader;
    private Integer vocabularyHeaderParent;
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
