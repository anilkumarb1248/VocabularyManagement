package com.vocabulary.learning.app.controller;

import com.vocabulary.learning.app.model.Word;
import com.vocabulary.learning.app.model.WordSearchRequest;
import com.vocabulary.learning.app.response.IndividualResponse;
import com.vocabulary.learning.app.response.ListResponse;
import com.vocabulary.learning.app.response.Status;
import com.vocabulary.learning.app.service.WordService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/words")
//@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200", "http://192.168.0.143:4200"})
public class WordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordController.class);

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }


    @PostMapping("/all")
    public Callable<ResponseEntity<ListResponse<Word>>> getAllWords(@RequestBody WordSearchRequest wordSearchRequest) {

        ListResponse<Word> listResponse = new ListResponse<>();
        List<Word> wordList = wordService.getAllWords(wordSearchRequest);

        if (!CollectionUtils.isEmpty(wordList)) {
            listResponse.setValues(wordList);
            listResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else {
            LOGGER.warn("No Words found");
            listResponse.setStatus(Status.NOT_FOUND);
            listResponse.setMsg("No Words found");
            return () -> new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Callable<ResponseEntity<IndividualResponse<Word>>> getWordDetails(@RequestParam("wordId") Integer wordId, @RequestParam("searchWord") String searchWord) {

        IndividualResponse<Word> individualResponse = new IndividualResponse<>();
        if (StringUtils.isBlank(searchWord) || wordId == null) {
            individualResponse.setStatus(Status.BAD_REQUEST);
            individualResponse.setMsg("Word or word Id is required");
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.BAD_REQUEST);
        }

        Word word = wordService.getWordDetails(wordId, searchWord);

        if (word != null) {
            individualResponse.setValue(word);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.OK);
        } else {
            LOGGER.warn("No word found: {}", searchWord);
            individualResponse.setStatus(Status.NOT_FOUND);
            individualResponse.setMsg("No word found");
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Callable<ResponseEntity<IndividualResponse<Word>>> insertWord(@RequestBody @Valid Word word) {

        IndividualResponse<Word> individualResponse = new IndividualResponse<>();
        try {
            wordService.insertWord(word);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while inserting the word ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/list-insert")
    public Callable<ResponseEntity<IndividualResponse<Word>>> insertWordsList(@RequestBody @Valid List<Word> wordList) {

        IndividualResponse<Word> individualResponse = new IndividualResponse<>();
        try {
            wordService.insertWordsList(wordList);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while inserting the words list ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public Callable<ResponseEntity<IndividualResponse<Word>>> updateWord(@RequestBody @Valid Word word) {

        IndividualResponse<Word> individualResponse = new IndividualResponse<>();
        try {
            wordService.updateWord(word);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while updating the word: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public Callable<ResponseEntity<IndividualResponse<Word>>> deleteWord(@RequestParam("wordId") Integer wordId, @RequestParam("searchWord") String searchWord) {
        IndividualResponse<Word> individualResponse = new IndividualResponse<>();
        if (StringUtils.isBlank(searchWord) && wordId == null) {
            individualResponse.setStatus(Status.BAD_REQUEST);
            individualResponse.setMsg("Word or word Id is required");
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            wordService.deleteWord(wordId, searchWord);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while deleting the word: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
