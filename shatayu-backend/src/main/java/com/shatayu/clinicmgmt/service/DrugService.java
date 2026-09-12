package com.shatayu.clinicmgmt.service;

import com.shatayu.clinicmgmt.entity.Drug;
import com.shatayu.clinicmgmt.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugService {

    @Autowired
    private DrugRepository drugRepository;

    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    public List<Drug> getActiveDrugs() {
        return drugRepository.findByInactiveIsNullOrInactiveNot('Y');
    }

    public List<Drug> searchDrugs(String name) {
        return drugRepository.findByDrugNameContainingIgnoreCase(name);
    }

    public Drug saveDrug(Drug drug) {
        return drugRepository.save(drug);
    }

    public void deleteDrug(Long drugId) {
        drugRepository.deleteById(drugId);
    }
}
