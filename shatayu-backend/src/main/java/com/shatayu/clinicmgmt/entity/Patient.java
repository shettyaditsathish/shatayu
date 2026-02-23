package com.shatayu.clinicmgmt.entity;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PATIENT_ID", nullable = false)
    private Long patientId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "AGE")
    private Long age;

    @Column(name = "SEX", nullable = false)
    private String sex;

    @Column(name = "INITIAL_DATE", nullable = false)
    private Date initialDate;

    @Column(name = "MOBILE1")
    private String MOBILE1;

    @Column(name = "MOBILE2")
    private String MOBILE2;

    @Column(name = "LANDLINE")
    private String landline;

    @Column(name = "KNOWN_CASES")
    private String knownCases;

    @Column(name = "HISTORY")
    private String history;

    @Column(name = "ADDRESS")
    private String address;

}
