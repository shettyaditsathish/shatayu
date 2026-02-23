package com.shatayu.clinicmgmt.service;

import com.shatayu.clinicmgmt.entity.PatientPanchkarma;
import com.shatayu.clinicmgmt.repository.PatientPanchkarmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PatientPanchkarmaService {

    @Autowired
    private PatientPanchkarmaRepository patientPanchkarmaRepository;

    public Page<PatientPanchkarma> getPanchkarmaByPatientId(Long patientId, int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset / limit, limit);
        return patientPanchkarmaRepository.findByPatientIdOrderByStartDateDesc(patientId, pageRequest);
    }

    public PatientPanchkarma savePatientPanchkarma(PatientPanchkarma patientPanchkarma) {
        return patientPanchkarmaRepository.save(patientPanchkarma);
    }
}
