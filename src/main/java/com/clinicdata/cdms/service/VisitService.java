package com.clinicdata.cdms.service;

import com.clinicdata.cdms.model.Patient;
import com.clinicdata.cdms.model.Visit;
import com.clinicdata.cdms.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    public void saveVisit(Visit visit) {
        visitRepository.save(visit);
    }

    public List<Visit> getVisitsByPatient(Patient patient) {
        return visitRepository.findByPatient(patient);
    }

    public List<Visit> getVisitsByDoctor(String doctorUsername) {
        return visitRepository.findByRecordedBy(doctorUsername);
    }

    public Visit getVisitById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    public void deleteVisit(Long id) {
        visitRepository.deleteById(id);
    }
}
