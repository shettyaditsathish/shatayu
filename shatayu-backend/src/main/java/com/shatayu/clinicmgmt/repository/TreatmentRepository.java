package com.shatayu.clinicmgmt.repository;

import com.shatayu.clinicmgmt.entity.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findByPatientIdOrderByTreatmentDateDesc(Long patientId);

    Page<Treatment> findByPatientIdOrderByTreatmentDateDesc(Long patientId, Pageable pageable);
}
