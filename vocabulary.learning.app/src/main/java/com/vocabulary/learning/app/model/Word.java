package com.vocabulary.learning.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Word {
    private Integer wordId;
    private String word;
    private String phonetics;
    private String synonyms;
    private String antonyms;
    private String notes;
    private String meaning;
    private LocalDateTime createdTimeStamp;
    private LocalDateTime updatedTimeStamp;

}
