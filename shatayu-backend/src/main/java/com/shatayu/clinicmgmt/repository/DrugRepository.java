package com.shatayu.clinicmgmt.repository;

import com.shatayu.clinicmgmt.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrugRepository extends JpaRepository<Drug, Long> {

    List<Drug> findByInactiveIsNullOrInactiveNot(Character inactive);

    List<Drug> findByDrugNameContainingIgnoreCase(String drugName);
}
