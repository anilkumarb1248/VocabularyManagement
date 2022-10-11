package com.vocabulary.learning.app.entity;

import com.vocabulary.learning.app.enums.MeaningTextType;
import com.vocabulary.learning.app.enums.MeaningType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALL_MEANINGS")
public class AllMeaningsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEANING_ID", unique = true)
    private Integer id;

    @Column(name="TITLE")
    private String title;

    @Column(name="MEANING_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private MeaningType meaningType;

    @Column(name="TEXT")
    private String text;

    @Column(name="MEANING_TEXT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private MeaningTextType meaningTextType;

    @Column(name="SCRIPT")
    private String script;

    @Column(name="MEANING")
    private String meanings;

    @Column(name="CREATE_TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false, updatable = false)
    private LocalDateTime createdTimeStamp;

    @Column(name="UPDATE_TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",insertable = false, updatable = false)
    private LocalDateTime updatedTimeStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MeaningType getMeaningType() {
        return meaningType;
    }

    public void setMeaningType(MeaningType meaningType) {
        this.meaningType = meaningType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MeaningTextType getMeaningTextType() {
        return meaningTextType;
    }

    public void setMeaningTextType(MeaningTextType meaningTextType) {
        this.meaningTextType = meaningTextType;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getMeanings() {
        return meanings;
    }

    public void setMeanings(String meanings) {
        this.meanings = meanings;
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
}
