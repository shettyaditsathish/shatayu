package com.shatayu.clinicmgmt.controller;

import com.shatayu.clinicmgmt.entity.Patient;
import com.shatayu.clinicmgmt.repository.PatientRepository;
import com.shatayu.clinicmgmt.service.PatientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PatientController {

    Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @GetMapping("/test")
    public List<Patient> helloWorld() {
        List<Patient> pt = patientRepository.findAll();
        return patientRepository.findAll();
    }

    @GetMapping("/search")
    public Page<Patient> searchPatients(@RequestParam String name,
                                        @RequestParam(defaultValue = "0") int offset,
                                        @RequestParam(defaultValue = "50") int limit) {
        logger.info("Searching patients with term: {}, offset: {}, limit: {}", name, offset, limit);
        return patientService.searchPatientsByName(name, offset, limit);
    }

    @PostMapping("/patient")
    public Patient savePatient(@RequestBody Patient patient) {
        logger.info("Saving patient: {}", patient);
        return patientService.savePatient(patient);
    }

}
