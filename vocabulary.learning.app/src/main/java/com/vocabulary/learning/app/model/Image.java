package com.vocabulary.learning.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Image {
    private Integer imageId;
    private String name;
    private byte[] image;
    private String type;
    private Integer verbId;
}
