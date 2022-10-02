package com.vocabulary.learning.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MEANING")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MeaningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MEANING_ID", unique = true, nullable = false)
    private Integer meaningId;

    @Column(name="MEANING")
    private String meaning;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VERB_ID", nullable = false, insertable = false, updatable = false)
    private VerbEntity verbEntity;
}
