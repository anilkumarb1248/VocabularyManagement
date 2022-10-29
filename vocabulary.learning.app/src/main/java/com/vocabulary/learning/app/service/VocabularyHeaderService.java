package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.entity.VocabularyHeaderEntity;
import com.vocabulary.learning.app.model.VocabularyHeader;
import com.vocabulary.learning.app.repository.VocabularyHeaderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VocabularyHeaderService {

    private final VocabularyHeaderRepository vocabularyHeaderRepository;

    @Autowired
    public VocabularyHeaderService(VocabularyHeaderRepository vocabularyHeaderRepository){
        this.vocabularyHeaderRepository = vocabularyHeaderRepository;
    }
    public List<VocabularyHeader> getAllVocabularyHeaders() {
        List<VocabularyHeaderEntity> vocabularyHeaderEntities = vocabularyHeaderRepository.findAll();
        List<VocabularyHeader> vocabularyHeaderList = new ArrayList<>();
        vocabularyHeaderEntities.forEach(entity -> {
            VocabularyHeader vocabularyHeader = new VocabularyHeader();
            BeanUtils.copyProperties(entity, vocabularyHeader);
            vocabularyHeaderList.add(vocabularyHeader);
        });
        return vocabularyHeaderList;
    }

    public VocabularyHeader getVocabularyHeaderDetails(Integer headerId) {
        Optional<VocabularyHeaderEntity> optional = vocabularyHeaderRepository.findById(headerId);
        if(optional.isPresent()){
            VocabularyHeader vocabularyHeader = new VocabularyHeader();
            BeanUtils.copyProperties(optional.get(), vocabularyHeader);
            return vocabularyHeader;
        }
        return null;
    }

    public void addVocabularyHeader(VocabularyHeader vocabularyHeader) {
        VocabularyHeaderEntity vocabularyHeaderEntity = new VocabularyHeaderEntity();
        BeanUtils.copyProperties(vocabularyHeader, vocabularyHeaderEntity);
        vocabularyHeaderRepository.save(vocabularyHeaderEntity);
    }

    public void deleteVocabularyHeader(Integer headerId) {
        vocabularyHeaderRepository.deleteById(headerId);
    }
}
