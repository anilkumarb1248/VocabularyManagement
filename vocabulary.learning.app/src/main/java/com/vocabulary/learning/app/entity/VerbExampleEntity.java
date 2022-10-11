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
@Table(name = "VERB_EXAMPLE")
public class VerbExampleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EXAMPLE_ID", unique = true)
    private Integer exampleId;

    @Column(name="EXAMPLE", nullable = false)
    private String example;

    @ManyToOne
    @JoinColumn(name = "VERB_ID", nullable = false, insertable = false, updatable = false)
    private VerbEntity verbEntity;

    public Integer getExampleId() {
        return exampleId;
    }

    public void setExampleId(Integer exampleId) {
        this.exampleId = exampleId;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public VerbEntity getVerbEntity() {
        return verbEntity;
    }

    public void setVerbEntity(VerbEntity verbEntity) {
        this.verbEntity = verbEntity;
    }
}
