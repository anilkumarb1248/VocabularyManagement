package com.vocabulary.learning.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "WORD_MEANING")
public class WordMeaningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="WORD_MEANING_ID", unique = true)
    private Integer wordMeaningId;

    @Column(name="WORD_MEANING")
    private String wordMeaning;

    @ManyToOne
    @JoinColumn(name = "WORD_ID", nullable = false, insertable = false, updatable = false)
    private WordEntity wordEntity;

}
