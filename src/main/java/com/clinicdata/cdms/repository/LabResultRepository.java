package com.clinicdata.cdms.repository;

import com.clinicdata.cdms.model.LabResult;
import com.clinicdata.cdms.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabResultRepository extends JpaRepository<LabResult, Long> {
    List<LabResult> findByVisit(Visit visit);
}
