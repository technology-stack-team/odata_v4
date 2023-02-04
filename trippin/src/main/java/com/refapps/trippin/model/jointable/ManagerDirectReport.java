package com.refapps.trippin.model.jointable;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ManagerDirectReport")
@Table(schema = "Trippin", name = "ManagerDirectReport")
@Data
public class ManagerDirectReport {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "DirectReport")
    private String directReport;
}
