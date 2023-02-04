package com.refapps.trippin.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "PlanItem")
@DiscriminatorValue(value = "1")
@Data
public class PlanItem extends BasePlanItem{
    public PlanItem() {
        super();
        type = "1";
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DocumentId", referencedColumnName = "DocId", insertable = false, updatable = false)
    private Document ticket;

    @Column(name =  "DocumentId")
    private String documentId;

}
