package com.vocabulary.learning.app.entity;

import com.vocabulary.learning.app.enums.LearningStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "VERB")
public class VerbEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="VERB_ID", unique = true)
    private Integer verbId;

    @Column(name="BASE_FORM", unique = true, nullable = false)
    private String baseForm;

    @Column(name="PAST_TENSE_FORM", nullable = false)
    private String pastTenseForm;

    @Column(name="PAST_PARTICIPLE_FORM", nullable = false)
    private String pastParticipleForm;

    @Column(name="THIRD_PERSON_BASE_FORM")
    private String thirdPersonBaseForm;

    @Column(name="PROGRESSIVE_FORM")
    private String progressiveForm;

    @Column(name="PHONETICS")
    private String phonetics;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="VERB_ID", nullable = false)
    private List<MeaningEntity> meanings;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="VERB_ID", nullable = false)
    private List<ExampleEntity> examples;

    @Column(name="LEARNING_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private LearningStatus learningStatus;

    @Column(name="CREATE_TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false, updatable = false)
    private LocalDateTime createdTimeStamp;

    @Column(name="UPDATE_TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",insertable = false, updatable = false)
    private LocalDateTime updatedTimeStamp;

    public Integer getVerbId() {
        return verbId;
    }

    public void setVerbId(Integer verbId) {
        this.verbId = verbId;
    }

    public String getBaseForm() {
        return baseForm;
    }

    public void setBaseForm(String baseForm) {
        this.baseForm = baseForm;
    }

    public String getPastTenseForm() {
        return pastTenseForm;
    }

    public void setPastTenseForm(String pastTenseForm) {
        this.pastTenseForm = pastTenseForm;
    }

    public String getPastParticipleForm() {
        return pastParticipleForm;
    }

    public void setPastParticipleForm(String pastParticipleForm) {
        this.pastParticipleForm = pastParticipleForm;
    }

    public String getThirdPersonBaseForm() {
        return thirdPersonBaseForm;
    }

    public void setThirdPersonBaseForm(String thirdPersonBaseForm) {
        this.thirdPersonBaseForm = thirdPersonBaseForm;
    }

    public String getProgressiveForm() {
        return progressiveForm;
    }

    public void setProgressiveForm(String progressiveForm) {
        this.progressiveForm = progressiveForm;
    }

    public String getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(String phonetics) {
        this.phonetics = phonetics;
    }

    public List<MeaningEntity> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<MeaningEntity> meanings) {
        this.meanings = meanings;
    }

    public List<ExampleEntity> getExamples() {
        return examples;
    }

    public void setExamples(List<ExampleEntity> examples) {
        this.examples = examples;
    }

    public LocalDateTime getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(LocalDateTime createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public LocalDateTime getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public void setUpdatedTimeStamp(LocalDateTime updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public LearningStatus getLearningStatus() {
        return learningStatus;
    }

    public void setLearningStatus(LearningStatus learningStatus) {
        this.learningStatus = learningStatus;
    }
}
