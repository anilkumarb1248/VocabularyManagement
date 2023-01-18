package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.entity.WordEntity;
import com.vocabulary.learning.app.exception.DuplicateWordException;
import com.vocabulary.learning.app.model.Word;
import com.vocabulary.learning.app.model.WordSearchRequest;
import com.vocabulary.learning.app.repository.WordRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WordService {

    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository){
        this.wordRepository = wordRepository;
    }

    public List<Word> getAllWords(WordSearchRequest request) {

        List<Word> wordList = new ArrayList<>();

        List<WordEntity> wordEntities;
        if (request == null || "All".equalsIgnoreCase(request.getSelectedLetter()) || StringUtils.isBlank(request.getSearchInput())) {
            wordEntities = findAllWords();
        } else {
            wordEntities = findAllWithPagination(request);
        }

        if (CollectionUtils.isNotEmpty(wordEntities)) {
            wordEntities.stream().forEach(entity -> {
                Word word = convertToDTO(entity);
                wordList.add(word);
            });
        }

        return wordList;
    }

    private List<WordEntity> findAllWords() {
        Sort sort = Sort.by(Sort.Direction.fromString("ASC"), "word");
        return wordRepository.findAll(sort);
    }

    private List<WordEntity> findAllWithPagination(WordSearchRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortOrder()), "word");
        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getPageSize(), sort);

        Page<WordEntity> page = null;
        if (StringUtils.isBlank(request.getSearchInput())) {
            page = wordRepository.findByWordLike(request.getSelectedLetter() + "%", pageable);
        } else {
            page = wordRepository.findByWordIgnoreCaseLike("%" + request.getSearchInput() + "%", pageable);
        }
        if (page != null) {
            return page.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public Word getWordDetails(Integer wordId, String searchWord) {

        Optional<WordEntity> wordEntityOptional = Optional.empty();
        if(wordId != null){
            wordEntityOptional = wordRepository.findById(wordId);
        }else if(StringUtils.isNotBlank(searchWord)){
            wordEntityOptional = wordRepository.findByWord(searchWord);
        }

        Word word = null;
        if(wordEntityOptional.isPresent()){
           word = convertToDTO(wordEntityOptional.get());
        }
        return word;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertWord(Word word) {
        if (!isDuplicateWord(word, true)) {
            WordEntity wordEntity = convertToEntity(word);
            wordRepository.save(wordEntity);
        } else {
            throw new DuplicateWordException("Duplicate word with : " + word.getWord());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertWordsList(List<Word> wordList) {
        List<WordEntity> wordEntities = new ArrayList<>();
        wordList.stream().forEach(word -> {
            if (!isDuplicateWord(word, true)) {
                wordEntities.add(convertToEntity(word));
            }
        });
        if (!CollectionUtils.isEmpty(wordEntities)) {
            wordRepository.saveAll(wordEntities);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateWord(Word word) {
        if (!isDuplicateWord(word, false)) {
            WordEntity wordEntity = convertToEntity(word);
            wordRepository.save(wordEntity);
        } else {
            throw new DuplicateWordException("Duplicate word with : " + word.getWord());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void deleteWord(Integer wordId, String searchWord) {
        if(wordId != null){
            wordRepository.deleteById(wordId);
        }else if(StringUtils.isNotBlank(searchWord)){
           wordRepository.deleteByWord(searchWord);
        }
    }

    private Word convertToDTO(WordEntity wordEntity){
        Word word = new Word();
        word.setWordId(wordEntity.getWordId());
        word.setWord(wordEntity.getWord());
        word.setPhonetics(wordEntity.getPhonetics());
        word.setSynonyms(wordEntity.getSynonyms());
        word.setAntonyms(wordEntity.getAntonyms());
        word.setNotes(wordEntity.getNotes());
        word.setMeaning(wordEntity.getMeaning());
        word.setCreatedTimeStamp(wordEntity.getCreatedTimeStamp());
        word.setUpdatedTimeStamp(wordEntity.getUpdatedTimeStamp());
        return word;
    }

    private WordEntity convertToEntity(Word word){
        WordEntity wordEntity = new WordEntity();
        wordEntity.setWordId(word.getWordId());
        wordEntity.setWord(word.getWord());
        wordEntity.setPhonetics(word.getPhonetics());
        wordEntity.setSynonyms(word.getSynonyms());
        wordEntity.setAntonyms(word.getAntonyms());
        wordEntity.setNotes(word.getNotes());
        wordEntity.setMeaning(word.getMeaning());
        return wordEntity;
    }

    private boolean isDuplicateWord(Word word, boolean newEntryFlag) {
        Optional<WordEntity> optional = wordRepository.findByWord(word.getWord());
        if (optional.isEmpty()) {
            return false;
        } else {
            if (newEntryFlag) {
                return true;
            } else {
                if (word.getWordId() == optional.get().getWordId()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

}
