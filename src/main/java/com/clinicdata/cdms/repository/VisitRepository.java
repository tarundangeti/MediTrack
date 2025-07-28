package com.clinicdata.cdms.repository;

import com.clinicdata.cdms.model.Visit;
import com.clinicdata.cdms.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    // Get all visits for a patient
    List<Visit> findByPatient(Patient patient);

    // Optional: filter by doctor
    List<Visit> findByRecordedBy(String doctorUsername);
}
