package com.refapps.trippin.model;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmMediaStream;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Advertisement")
@Table(schema = "\"Trippin\"", name = "\"Advertisement\"")
@Data
public class Advertisement {
    @Id
    @Column(name = "\"Id\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"Image\"")
    @EdmMediaStream(contentTypeAttribute = "mimeType")
    private byte[] image;

    @EdmIgnore
    @Column(name = "\"MimeType\"")
    private String mimeType;
}
