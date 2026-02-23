package com.shatayu.clinicmgmt.repository;

import com.shatayu.clinicmgmt.entity.Patient;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    @Query("SELECT p FROM Patient p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> searchByName(@Param("name") String name);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Patient> searchByName(@Param("name") String name, Pageable pageable);

    // Two word search - matches firstName + lastName OR lastName + firstName
    @Query("SELECT p FROM Patient p WHERE " +
            "(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) AND LOWER(p.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) "
            +
            "OR (LOWER(p.firstName) LIKE LOWER(CONCAT('%', :lastName, '%')) AND LOWER(p.lastName) LIKE LOWER(CONCAT('%', :firstName, '%')))")
    List<Patient> searchByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("SELECT p FROM Patient p WHERE " +
            "(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) AND LOWER(p.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) "
            +
            "OR (LOWER(p.firstName) LIKE LOWER(CONCAT('%', :lastName, '%')) AND LOWER(p.lastName) LIKE LOWER(CONCAT('%', :firstName, '%')))")
    Page<Patient> searchByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName, Pageable pageable);
}