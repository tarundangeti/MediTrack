package com.clinicdata.cdms.repository;

import com.clinicdata.cdms.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByAssignedDoctor(String doctorUsername);

    List<Patient> findByAssignedToUsername(String username); // ✅ Correct method for Data Entry users

    List<Patient> findByNameContainingIgnoreCase(String keyword);
}
