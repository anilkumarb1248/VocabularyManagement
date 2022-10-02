package com.vocabulary.learning.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Verb {
    private Integer verbId;
    private String baseForm;
    private String pastTenseForm;
    private String pastParticipleForm;
    private String thirdPersonBaseForm;
    private String progressiveForm;
    private String phonetics;
    private List<String> meanings = new ArrayList<>();
//    private List<String> images = new ArrayList<>();
    private List<String> examples = new ArrayList<>();
    private List<String> baseFormExamples = new ArrayList<>();
    private List<String> pastTenseExample = new ArrayList<>();
    private List<String> pastParticipleFormExample = new ArrayList<>();
    private LocalDateTime createdTimeStamp;
    private LocalDateTime updatedTimeStamp;

    public Verb(Integer verbId, String baseForm, String pastTenseForm, String pastParticipleForm, String thirdPersonBaseForm, String progressiveForm, String phonetics, List<String> meanings, List<String> examples, List<String> baseFormExamples, List<String> pastTenseExample, List<String> pastParticipleFormExample, LocalDateTime createdTimeStamp, LocalDateTime updatedTimeStamp) {
        this.verbId = verbId;
        this.baseForm = baseForm;
        this.pastTenseForm = pastTenseForm;
        this.pastParticipleForm = pastParticipleForm;
        this.thirdPersonBaseForm = thirdPersonBaseForm;
        this.progressiveForm = progressiveForm;
        this.phonetics = phonetics;
        this.meanings = meanings;
        this.examples = examples;
        this.baseFormExamples = baseFormExamples;
        this.pastTenseExample = pastTenseExample;
        this.pastParticipleFormExample = pastParticipleFormExample;
        this.createdTimeStamp = createdTimeStamp;
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public String toStringFormat() {
        return new StringBuilder()
                .append(baseForm).append(", ")
                .append(pastTenseForm).append(", ")
                .append(pastParticipleForm).append(", ")
                .append(thirdPersonBaseForm).append(", ")
                .append(progressiveForm).toString();
    }
}
