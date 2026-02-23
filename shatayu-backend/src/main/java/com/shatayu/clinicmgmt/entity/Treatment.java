package com.shatayu.clinicmgmt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "treatment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Treatment {

    @Id
    @Column(name = "TREATMENT_ID", nullable = false)
    private Long treatmentId;

    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "TREATMENT_DATE", nullable = false)
    private Date treatmentDate;

    @Column(name = "SIGNS_SYMPTOMS", nullable = false, columnDefinition = "mediumtext")
    private String signsSymptoms;

    @Column(name = "TONGUE", nullable = false)
    private String tongue;

    @Column(name = "PULSE", nullable = false)
    private String pulse;

    @Column(name = "RESULTS", columnDefinition = "mediumtext")
    private String results;

    @Column(name = "Paid")
    private String paid;

    @Column(name = "AMOUNT_PAID")
    private Float amountPaid;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "BALANCE")
    private Float balance;

    @OneToMany(mappedBy = "treatment", fetch = FetchType.EAGER)
    private List<TreatmentDrug> treatmentDrugs;
}
