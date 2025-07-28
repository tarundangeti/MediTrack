package com.clinicdata.cdms.service;

import com.clinicdata.cdms.model.LabResult;
import com.clinicdata.cdms.model.Visit;
import com.clinicdata.cdms.repository.LabResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabResultService {

    @Autowired
    private LabResultRepository labResultRepository;

    public void save(LabResult labResult) {
        labResultRepository.save(labResult);
    }

    public List<LabResult> getByVisit(Visit visit) {
        return labResultRepository.findByVisit(visit);
    }
}
