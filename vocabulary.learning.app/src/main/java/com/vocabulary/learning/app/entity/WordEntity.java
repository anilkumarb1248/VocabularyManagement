package com.vocabulary.learning.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
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
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "WORD")
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="WORD_ID", unique = true)
    private Integer wordId;

    @Column(name="WORD", unique = true)
    private String word;

    @Column(name="PHONETICS")
    private String phonetics;

    @Column(name="SYNONYMS")
    private String synonyms;

    @Column(name="ANTONYMS")
    private String antonyms;

    @Column(name="NOTES")
    private String notes;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="WORD_ID", nullable = false)
    private List<WordMeaningEntity> wordMeanings;

    @Column(name="CREATE_TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false, updatable = false)
    private LocalDateTime createdTimeStamp;

    @Column(name="UPDATE_TIMESTAMP", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",insertable = false, updatable = false)
    private LocalDateTime updatedTimeStamp;

}
