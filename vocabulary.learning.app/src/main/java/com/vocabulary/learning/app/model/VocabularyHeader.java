package com.vocabulary.learning.app.model;

import com.vocabulary.learning.app.enums.HeaderType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VocabularyHeader {
    private Integer headerId;
    @NotBlank(message = "Header Name should not be blank")
    private String header;
    private Integer parentId;
    @NotNull(message = "Header type should not be null")
    private HeaderType headerType;

    public Integer getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Integer headerId) {
        this.headerId = headerId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public HeaderType getHeaderType() {
        return headerType;
    }

    public void setHeaderType(HeaderType headerType) {
        this.headerType = headerType;
    }
}
