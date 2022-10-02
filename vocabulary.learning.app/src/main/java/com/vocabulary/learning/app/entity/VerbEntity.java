package com.vocabulary.learning.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@NoArgsConstructor
@Setter
@Getter
@ToString
public class VerbEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="VERB_ID", unique = true, nullable = false)
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="MEANING_ID", nullable = true)
    private List<MeaningEntity> meanings;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name="IMAGE_ID", nullable = true)
//    private List<ImageEntity> images;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="EXAMPLE_ID", nullable = true)
    private List<ExampleEntity> examples;


//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name="EXAMPLE_ID", nullable = true)
//    private List<Example> baseFormExamples;
//
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name="EXAMPLE_ID", nullable = true)
//    private List<Example> pastTenseExample;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name="EXAMPLE_ID", nullable = true)
//    @JoinTable(name = "VERB_EXAMPLE", joinColumns = @JoinColumn(name = "VERB_ID"),
//        inverseJoinColumns = @JoinColumn(name = "EXAMPLE_ID")
//    )
//    private List<Example> pastParticipleFormExample;


    @Column(name="CREAT_TS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false, updatable = false)
    private LocalDateTime createdTimeStamp;

    @Column(name="CREAT_TS", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",insertable = false, updatable = false)
    private LocalDateTime updatedTimeStamp;


}
