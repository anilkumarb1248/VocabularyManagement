package com.vocabulary.learning.app.model;

import com.vocabulary.learning.app.enums.LearningStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Verb {
    private Integer verbId;

    @NotBlank(message = "Base Form should not be blank")
    private String baseForm;
    private String pastTenseForm;
    private String pastParticipleForm;
    private String thirdPersonBaseForm;
    private String progressiveForm;
    private String phonetics;
    private List<String> meanings = new ArrayList<>();
    private List<String> examples = new ArrayList<>();
    private LearningStatus learningStatus;
    private LocalDateTime createdTimeStamp;
    private LocalDateTime updatedTimeStamp;

    public String toStringFormat() {
        return new StringBuilder()
                .append(baseForm).append(", ")
                .append(pastTenseForm).append(", ")
                .append(pastParticipleForm).append(", ")
                .append(thirdPersonBaseForm).append(", ")
                .append(progressiveForm).toString();
    }
}
