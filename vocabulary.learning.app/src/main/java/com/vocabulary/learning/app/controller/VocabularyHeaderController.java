package com.vocabulary.learning.app.controller;

import com.vocabulary.learning.app.model.VocabularyHeader;
import com.vocabulary.learning.app.response.IndividualResponse;
import com.vocabulary.learning.app.response.ListResponse;
import com.vocabulary.learning.app.response.Status;
import com.vocabulary.learning.app.service.VocabularyHeaderService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/VocabularyHeaders")
//@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200", "http://192.168.0.143:4200"})
public class VocabularyHeaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VocabularyHeaderController.class);

    private final VocabularyHeaderService vocabularyHeaderService;

    @Autowired
    public VocabularyHeaderController(VocabularyHeaderService vocabularyHeaderService) {
        this.vocabularyHeaderService = vocabularyHeaderService;
    }

    @GetMapping
    public Callable<ResponseEntity<ListResponse<VocabularyHeader>>> getAllParentVocabularyHeaders(){
        ListResponse<VocabularyHeader> listResponse = new ListResponse<>();
        List<VocabularyHeader> allVocabularyHeaders = vocabularyHeaderService.getAllParentVocabularyHeaders();

        if (!CollectionUtils.isEmpty(allVocabularyHeaders)) {
            listResponse.setValues(allVocabularyHeaders);
            listResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else {
            LOGGER.warn("No Headers found");
            listResponse.setStatus(Status.NOT_FOUND);
            listResponse.setMsg("No Headers found");
            return () -> new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/header/{headerId}")
    public Callable<ResponseEntity<IndividualResponse<VocabularyHeader>>> getVocabularyHeaderDetails(@PathVariable Integer headerId){
        IndividualResponse<VocabularyHeader> listResponse = new IndividualResponse<>();
        VocabularyHeader vocabularyHeader = vocabularyHeaderService.getVocabularyHeaderDetails(headerId);

        if (vocabularyHeader != null) {
            listResponse.setValue(vocabularyHeader);
            listResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else {
            LOGGER.warn("No Header found with header id: {}", headerId);
            listResponse.setStatus(Status.NOT_FOUND);
            listResponse.setMsg("No Header found with id: "+ headerId);
            return () -> new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/parent/{parentId}")
    public Callable<ResponseEntity<ListResponse<VocabularyHeader>>> getAllChildHeaders(@PathVariable Integer parentId){
        ListResponse<VocabularyHeader> listResponse = new ListResponse<>();
        List<VocabularyHeader> vocabularyHeaderList = vocabularyHeaderService.getAllChildHeaders(parentId);

        if (CollectionUtils.isNotEmpty(vocabularyHeaderList)) {
            listResponse.setValues(vocabularyHeaderList);
            listResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else {
            LOGGER.warn("No Header found with header id: {}", parentId);
            listResponse.setStatus(Status.NOT_FOUND);
            listResponse.setMsg("No Header found with id: "+ parentId);
            return () -> new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Callable<ResponseEntity<IndividualResponse<VocabularyHeader>>> addVocabularyHeader(@RequestBody @Valid VocabularyHeader vocabularyHeader) {

        IndividualResponse<VocabularyHeader> individualResponse = new IndividualResponse<>();
        try {
            vocabularyHeaderService.addVocabularyHeader(vocabularyHeader);
            individualResponse.setStatus(Status.CREATED);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while inserting the vocabulary header: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/list")
    public Callable<ResponseEntity<IndividualResponse<VocabularyHeader>>> addVocabularyHeadersList(@RequestBody @Valid List<VocabularyHeader> vocabularyHeaderList) {

        IndividualResponse<VocabularyHeader> individualResponse = new IndividualResponse<>();
        try {
            vocabularyHeaderService.addVocabularyHeadersList(vocabularyHeaderList);
            individualResponse.setStatus(Status.CREATED);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while inserting the vocabulary header: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public Callable<ResponseEntity<IndividualResponse<VocabularyHeader>>> updateVocabularyHeader(@RequestBody @Valid VocabularyHeader vocabularyHeader) {

        IndividualResponse<VocabularyHeader> individualResponse = new IndividualResponse<>();
        if(vocabularyHeader.getHeaderId() == null){
            individualResponse.setStatus(Status.BAD_REQUEST);
            individualResponse.setMsg("Header Id should not be null to update");
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            vocabularyHeaderService.updateVocabularyHeader(vocabularyHeader);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while inserting the vocabulary header: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{headerId}")
    public Callable<ResponseEntity<IndividualResponse<VocabularyHeader>>> deleteVocabularyHeader(@PathVariable Integer headerId) {
        IndividualResponse<VocabularyHeader> individualResponse = new IndividualResponse<>();
        if (headerId == null) {
            individualResponse.setStatus(Status.BAD_REQUEST);
            individualResponse.setMsg("Vocabulary Header Id must not be null");
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            vocabularyHeaderService.deleteVocabularyHeader(headerId);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while deleting the vocabulary header: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
