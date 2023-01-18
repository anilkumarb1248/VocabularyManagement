package com.vocabulary.learning.app.model;

import com.vocabulary.learning.app.enums.MeaningTextType;
import com.vocabulary.learning.app.enums.MeaningType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AllMeanings {
    private Integer id;
    private String title;
    private MeaningType meaningType;
    private String text;
    private MeaningTextType meaningTextType;
    private String script;
    private String meanings;
    private LocalDateTime createdTimeStamp;
    private LocalDateTime updatedTimeStamp;
}
