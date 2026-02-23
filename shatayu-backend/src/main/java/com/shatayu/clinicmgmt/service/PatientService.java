package com.shatayu.clinicmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shatayu.clinicmgmt.entity.Patient;
import com.shatayu.clinicmgmt.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Page<Patient> searchPatientsByName(String name, int offset, int limit) {
        if (name == null || name.trim().isEmpty()) {
            return Page.empty();
        }

        name = name.trim();
        PageRequest pageRequest = PageRequest.of(offset / limit, limit);

        // Check if the name contains a space
        if (name.contains(" ")) {
            // Split the name into two parts
            String[] nameParts = name.split("\\s+", 2);
            String firstPart = nameParts[0];
            String secondPart = nameParts[1];

            // Search with both firstName-lastName and lastName-firstName combinations
            return patientRepository.searchByFullName(firstPart, secondPart, pageRequest);
        } else {
            // Single word - search in both firstName and lastName
            return patientRepository.searchByName(name, pageRequest);
        }
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

}
