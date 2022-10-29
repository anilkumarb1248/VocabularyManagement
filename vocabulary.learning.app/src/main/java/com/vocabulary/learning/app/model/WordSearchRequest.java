package com.vocabulary.learning.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WordSearchRequest {
    private String searchInput;
    private String sortOrder;
    private String selectedLetter;
    private Integer pageSize;
    private Integer currentPage;
}
