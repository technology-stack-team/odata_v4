package org.example.trippin.model;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "PlanItem")
@Table(name = "BasePlanItem", schema = "\"Trippin\"")
public class PlanItem extends BasePlanItem{
}
