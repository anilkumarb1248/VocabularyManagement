package com.vocabulary.learning.app.entity;

import com.vocabulary.learning.app.enums.HeaderType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VOCABULARY_HEADER")
public class VocabularyHeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="HEADER_ID", unique = true, nullable = false)
    private Integer headerId;

    @Column(name="HEADER",  nullable = false)
    private String header;

    @Column(name="PARENT_HEADER")
    private Integer parentId;

    @Column(name="HEADER_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
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
