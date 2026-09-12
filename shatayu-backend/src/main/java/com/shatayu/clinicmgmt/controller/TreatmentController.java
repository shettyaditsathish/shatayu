package com.shatayu.clinicmgmt.controller;

import com.shatayu.clinicmgmt.entity.PatientPanchkarma;
import com.shatayu.clinicmgmt.entity.Treatment;
import com.shatayu.clinicmgmt.service.PatientPanchkarmaService;
import com.shatayu.clinicmgmt.service.TreatmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TreatmentController {

    Logger logger = LoggerFactory.getLogger(TreatmentController.class);

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private PatientPanchkarmaService patientPanchkarmaService;

    @GetMapping("/treatments")
    public Page<Treatment> getTreatmentsByPatientId(@RequestParam Long patientId,
                                                     @RequestParam(defaultValue = "0") int offset,
                                                     @RequestParam(defaultValue = "50") int limit) {
        logger.info("Fetching treatments for patientId: {}, offset: {}, limit: {}", patientId, offset, limit);
        return treatmentService.getTreatmentsByPatientId(patientId, offset, limit);
    }

    @GetMapping("/panchkarma")
    public Page<PatientPanchkarma> getPanchkarmaByPatientId(@RequestParam Long patientId,
                                                             @RequestParam(defaultValue = "0") int offset,
                                                             @RequestParam(defaultValue = "50") int limit) {
        logger.info("Fetching panchkarma treatments for patientId: {}, offset: {}, limit: {}", patientId, offset, limit);
        return patientPanchkarmaService.getPanchkarmaByPatientId(patientId, offset, limit);
    }

    @PostMapping("/treatment")
    public Treatment saveTreatment(@RequestBody Treatment treatment) {
        logger.info("Saving treatment: {}", treatment);
        return treatmentService.saveTreatment(treatment);
    }

    @PostMapping("/patientPanchkarma")
    public PatientPanchkarma savePatientPanchkarma(@RequestBody PatientPanchkarma patientPanchkarma) {
        logger.info("Saving patient panchkarma: {}", patientPanchkarma);
        return patientPanchkarmaService.savePatientPanchkarma(patientPanchkarma);
    }

    @GetMapping("/treatmentsByDate")
    public Page<Treatment> getTreatmentsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "500") int limit) {
        logger.info("Fetching treatments from {} to {}", startDate, endDate);
        return treatmentService.getTreatmentsByDateRange(startDate, endDate, offset, limit);
    }

    @GetMapping("/panchkarmaByDate")
    public Page<PatientPanchkarma> getPanchkarmaByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "500") int limit) {
        logger.info("Fetching panchkarma treatments from {} to {}", startDate, endDate);
        return patientPanchkarmaService.getPanchkarmaByDateRange(startDate, endDate, offset, limit);
    }
}
