package com.clinicdata.cdms.service;

import com.clinicdata.cdms.model.Patient;
import com.clinicdata.cdms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Create or update patient
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Get patient by ID
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    // Delete patient
    public void deletePatientById(Long id) {
        patientRepository.deleteById(id);
    }

    // Search patients by name
    public List<Patient> searchPatientsByName(String keyword) {
        return patientRepository.findByNameContainingIgnoreCase(keyword);
    }
    // Get patients for a specific doctor
    public List<Patient> getPatientsByDoctor(String doctorName) {
        return patientRepository.findByAssignedDoctor(doctorName);
    }

	public List<Patient> getPatientsByDataEntry(String dataEntryUsername) {
		// TODO Auto-generated method stub
		return patientRepository.findByAssignedToUsername(dataEntryUsername);
	}
}
