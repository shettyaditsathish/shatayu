package com.shatayu.clinicmgmt.controller;

import com.shatayu.clinicmgmt.entity.Drug;
import com.shatayu.clinicmgmt.service.DrugService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class DrugController {

    Logger logger = LoggerFactory.getLogger(DrugController.class);

    @Autowired
    private DrugService drugService;

    @GetMapping("/drugs")
    public List<Drug> getAllDrugs() {
        logger.info("Fetching all drugs");
        return drugService.getAllDrugs();
    }

    @GetMapping("/drugs/active")
    public List<Drug> getActiveDrugs() {
        logger.info("Fetching active drugs");
        return drugService.getActiveDrugs();
    }

    @GetMapping("/drugs/search")
    public List<Drug> searchDrugs(@RequestParam String name) {
        logger.info("Searching drugs with name: {}", name);
        return drugService.searchDrugs(name);
    }

    @PostMapping("/drug")
    public Drug saveDrug(@RequestBody Drug drug) {
        logger.info("Saving drug: {}", drug);
        return drugService.saveDrug(drug);
    }

    @DeleteMapping("/drug/{drugId}")
    public void deleteDrug(@PathVariable Long drugId) {
        logger.info("Deleting drug with id: {}", drugId);
        drugService.deleteDrug(drugId);
    }
}
