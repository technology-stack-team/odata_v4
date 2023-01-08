package com.refapps.trippin.model;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmMediaStream;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "Document")
@Table(schema = "\"Trippin\"", name = "\"Document\"")
@Data
public class Document {
    @Id
    @Column(name = "\"DocId\"", nullable = false)
    private String docId;

    @Column(name = "\"DocName\"")
    private String docName;

    @Column(name = "\"Content\"")
    @EdmMediaStream(contentTypeAttribute = "docFileType")
    private byte[] content;

    @Column(name = "\"DocFileType\"")
    private String docFileType;

    @Column(name = "\"DocLocation\"")
    private String docLocation;

    @Column(name = "\"DocSize\"")
    private String docSize;

    @OneToOne(mappedBy = "ticket")
    private PlanItem planItem;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"PersonId\"", insertable = false, updatable = false)
    private Person person;

    @Column(name = "\"PersonId\"")
    private String personId;
}
