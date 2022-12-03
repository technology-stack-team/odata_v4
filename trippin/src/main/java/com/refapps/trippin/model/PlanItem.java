package com.refapps.trippin.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "PlanItem")
@DiscriminatorValue(value = "1")
public class PlanItem extends BasePlanItem{
    public PlanItem() {
        super();
        dType = "1";
    }

}
