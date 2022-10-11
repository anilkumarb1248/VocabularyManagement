package com.vocabulary.learning.app.controller;

import com.vocabulary.learning.app.model.AllMeanings;
import com.vocabulary.learning.app.request.AllMeaningsRequest;
import com.vocabulary.learning.app.response.IndividualResponse;
import com.vocabulary.learning.app.response.ListResponse;
import com.vocabulary.learning.app.response.Status;
import com.vocabulary.learning.app.service.AllMeaningsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/meanings")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200",
        "http://192.168.0.143:4200",
        "http://192.168.0.1:4200",
        "http://desktop-ghd0jav:4200"
    })
public class AllMeaningsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllMeaningsController.class);

    private final AllMeaningsService allMeaningsService;

    @Autowired
    public AllMeaningsController(AllMeaningsService allMeaningsService) {
        this.allMeaningsService = allMeaningsService;
    }

    @PostMapping("/all")
    public Callable<ResponseEntity<ListResponse<AllMeanings>>> getMeanings(@RequestBody AllMeaningsRequest allMeaningsRequest){
        ListResponse<AllMeanings> listResponse = new ListResponse<>();
        List<AllMeanings> allMeaningsList = allMeaningsService.getAllMeanings(allMeaningsRequest);

        if (allMeaningsList != null) {
            listResponse.setValues(allMeaningsList);
            listResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else {
            LOGGER.warn("No Meaning records found");
            listResponse.setStatus(Status.NOT_FOUND);
            listResponse.setMsg("No Meaning records found");
            return () -> new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Callable<ResponseEntity<IndividualResponse<AllMeanings>>> createMeaning(@RequestBody @Valid AllMeanings allMeanings){
        IndividualResponse<AllMeanings> individualResponse = new IndividualResponse<>();
        try {
            allMeaningsService.createMeaning(allMeanings);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while creating the meaning: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public Callable<ResponseEntity<IndividualResponse<AllMeanings>>> updateMeaning(@RequestBody @Valid AllMeanings allMeanings){
        IndividualResponse<AllMeanings> individualResponse = new IndividualResponse<>();
        try {
            allMeaningsService.updateMeaning(allMeanings);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while updating meaning: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public Callable<ResponseEntity<IndividualResponse<AllMeanings>>> deleteMeaning(@PathVariable Integer id){
        IndividualResponse<AllMeanings> individualResponse = new IndividualResponse<>();
        try {
            allMeaningsService.deleteMeaning(id);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while deleting meaning: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
