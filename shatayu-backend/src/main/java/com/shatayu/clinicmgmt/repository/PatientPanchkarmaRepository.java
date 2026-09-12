package com.shatayu.clinicmgmt.repository;

import com.shatayu.clinicmgmt.entity.PatientPanchkarma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PatientPanchkarmaRepository extends JpaRepository<PatientPanchkarma, Long> {

    List<PatientPanchkarma> findByPatientIdOrderByStartDateDesc(Long patientId);

    Page<PatientPanchkarma> findByPatientIdOrderByStartDateDesc(Long patientId, Pageable pageable);

    Page<PatientPanchkarma> findByStartDateBetweenOrderByStartDateDesc(Date startDate, Date endDate, Pageable pageable);
}
