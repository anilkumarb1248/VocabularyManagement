package com.vocabulary.learning.app.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IMAGE_ID", unique = true, nullable = false)
    private Integer imageId;

    @Column(name="NAME")
    private String name;

    @Column(name="IMAGE", length = 1000)
    private byte[] image;

    @Column(name = "type")
    private String type;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "VERB_ID", nullable = false, insertable = false, updatable = false)
//    private VerbEntity verbEntity;

    @Column(name = "verbId")
    private Integer verbId;

}
