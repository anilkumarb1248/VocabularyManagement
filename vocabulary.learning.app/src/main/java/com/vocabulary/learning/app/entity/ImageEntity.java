package com.vocabulary.learning.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "IMAGE")
@NoArgsConstructor
@Setter
@Getter
public class ImageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IMAGE_ID", unique = true)
    private Integer imageId;

    @Column(name="NAME")
    private String name;

    @Lob
    @Column(name="IMAGE", length=100000)
    private byte[] image;

    @Column(name = "type")
    private String type;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "VERB_ID", nullable = false, insertable = false, updatable = false)
//    private VerbEntity verbEntity;

    @Column(name="VERB_ID", nullable = false, unique = true)
    private Integer verbId;

}
