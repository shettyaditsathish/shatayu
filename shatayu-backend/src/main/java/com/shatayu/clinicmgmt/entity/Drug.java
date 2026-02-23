package com.shatayu.clinicmgmt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drug")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drug {

    @Id
    @Column(name = "DRUG_ID", nullable = false)
    private Long drugId;

    @Column(name = "DRUG_NAME", nullable = false)
    private String drugName;

    @Column(name = "DRUG_COST")
    private Float drugCost;

    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "QUANTITY")
    private Long quantity;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "INACTIVE")
    private Character inactive;
}
