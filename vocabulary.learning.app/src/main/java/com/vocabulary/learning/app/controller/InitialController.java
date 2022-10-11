package com.vocabulary.learning.app.controller;

import com.vocabulary.learning.app.model.Verb;
import com.vocabulary.learning.app.response.IndividualResponse;
import com.vocabulary.learning.app.response.Status;
import com.vocabulary.learning.app.service.InitialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/initial")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200",
        "http://192.168.0.143:4200",
        "http://192.168.0.1:4200",
        "http://desktop-ghd0jav:4200"
})
public class InitialController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitialController.class);

    private final InitialService initialService;

    @Autowired
    public InitialController(InitialService initialService) {
        this.initialService = initialService;
    }


    @GetMapping
    public Callable<ResponseEntity<IndividualResponse<Verb>>> setupInitial() {
        IndividualResponse<Verb> individualResponse = new IndividualResponse<>();
        try {
            initialService.setupInitial();
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while setup: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
