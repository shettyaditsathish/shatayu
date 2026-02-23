package com.shatayu.clinicmgmt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "patientpanchkarma")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientPanchkarma {

    @Id
    @Column(name = "PATIENT_PANCH_ID", nullable = false)
    private Long patientPanchId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PANCHKARMA_ID")
    private Panchkarma panchkarma;

    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "RESULTS")
    private String results;

    @Column(name = "AMOUNT_PAID")
    private Float amountPaid;

    @Column(name = "Paid")
    private String paid;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "BALANCE")
    private Float balance;
}
