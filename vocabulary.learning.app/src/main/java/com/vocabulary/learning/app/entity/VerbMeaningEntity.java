package com.vocabulary.learning.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "VERB_MEANING")
public class VerbMeaningEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEANING_ID", unique = true)
    private Integer meaningId;

    @Column(name="MEANING")
    private String meaning;

    @ManyToOne
    @JoinColumn(name = "VERB_ID", nullable = false, insertable = false, updatable = false)
    private VerbEntity verbEntity;
    public Integer getMeaningId() {
        return meaningId;
    }

    public void setMeaningId(Integer meaningId) {
        this.meaningId = meaningId;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public VerbEntity getVerbEntity() {
        return verbEntity;
    }

    public void setVerbEntity(VerbEntity verbEntity) {
        this.verbEntity = verbEntity;
    }
}
