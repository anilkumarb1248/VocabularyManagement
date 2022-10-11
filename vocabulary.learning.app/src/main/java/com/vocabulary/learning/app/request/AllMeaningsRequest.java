package com.vocabulary.learning.app.request;

import com.vocabulary.learning.app.enums.MeaningType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AllMeaningsRequest {

    private MeaningType meaningType;
    private String sortOrder;
    private Integer pageSize;
    private Integer currentPage;
}
