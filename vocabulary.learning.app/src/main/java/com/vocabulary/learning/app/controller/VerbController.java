package com.vocabulary.learning.app.controller;

import com.vocabulary.learning.app.model.Verb;
import com.vocabulary.learning.app.response.IndividualResponse;
import com.vocabulary.learning.app.response.ListResponse;
import com.vocabulary.learning.app.response.Status;
import com.vocabulary.learning.app.service.VerbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/VerbsLearning")
@CrossOrigin(origins = "http://localhost:4200")
public class VerbController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerbController.class);

    private final VerbService verbService;

    @Autowired
    public VerbController(VerbService verbService) {
        this.verbService = verbService;
    }

    @GetMapping
    public Callable<ResponseEntity<ListResponse<Verb>>> getAllVerbs() {
        List<Verb> verbList = verbService.getAllVerbs();
        ListResponse<Verb> listResponse = new ListResponse<>();

        if (!CollectionUtils.isEmpty(verbList)) {
            listResponse.setValues(verbList);
            listResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else {
            LOGGER.warn("No Verb records found");
            listResponse.setStatus(Status.NOT_FOUND);
            listResponse.setMsg("No records found");
            return () -> new ResponseEntity<>(listResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/verbs")
    public Callable<ResponseEntity<IndividualResponse<Verb>>> insertVerbs(@RequestBody List<Verb> verbList) {

        IndividualResponse<Verb> individualResponse = new IndividualResponse<>();
        try {
            verbService.insertVerbs(verbList);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while inserting the verbs: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verb")
    public Callable<ResponseEntity<IndividualResponse<Verb>>> insertVerb(@RequestBody Verb verb) {

        IndividualResponse<Verb> individualResponse = new IndividualResponse<>();
        try {
            verbService.insertVerb(verb);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while inserting the verbs: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
