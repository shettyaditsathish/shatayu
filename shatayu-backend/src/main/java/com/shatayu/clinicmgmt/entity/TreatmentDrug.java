package com.shatayu.clinicmgmt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "treatmentdrug")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreatmentDrug {

    @Id
    @Column(name = "TREATMENT_DRUG_ID", nullable = false)
    private Long treatmentDrugId;

    @ManyToOne
    @JoinColumn(name = "TREATMENT_ID")
    @JsonIgnore
    private Treatment treatment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DRUG_ID")
    private Drug drug;

    @Column(name = "DURATION", nullable = false)
    private String duration;

    @Column(name = "NOOFDAYS")
    private Integer noOfDays;
}
