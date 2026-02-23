package com.shatayu.clinicmgmt.service;

import com.shatayu.clinicmgmt.entity.Treatment;
import com.shatayu.clinicmgmt.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    public Page<Treatment> getTreatmentsByPatientId(Long patientId, int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset / limit, limit);
        return treatmentRepository.findByPatientIdOrderByTreatmentDateDesc(patientId, pageRequest);
    }

    public Treatment saveTreatment(Treatment treatment) {
        treatment.setTreatmentDrugs(null);
        return treatmentRepository.save(treatment);
    }
}
