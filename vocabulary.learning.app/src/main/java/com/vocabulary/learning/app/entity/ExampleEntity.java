package com.vocabulary.learning.app.entity;

import com.vocabulary.learning.app.enums.ExampleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EXAMPLE")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ExampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EXAMPLE_ID", unique = true, nullable = false)
    private Integer exampleId;

    @Column(name="EXAMPLE", nullable = false)
    private String example;

    @Column(name="EXAMPLE_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExampleType exampleType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VERB_ID", nullable = false, insertable = false, updatable = false)
    private VerbEntity verbEntity;

}
