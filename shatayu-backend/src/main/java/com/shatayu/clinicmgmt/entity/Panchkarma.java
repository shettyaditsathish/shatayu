package com.shatayu.clinicmgmt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "panchkarma")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Panchkarma {

    @Id
    @Column(name = "PANCHKARMA_ID", nullable = false)
    private Long panchkarmaId;

    @Column(name = "PANCHKARMA_NAME", nullable = false)
    private String panchkarmaName;

    @Column(name = "COST")
    private Float cost;

    @Column(name = "PROCD")
    private String procd;

    @Column(name = "DISEASES")
    private String diseases;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "NOOFDAYS")
    private Long noOfDays;
}
