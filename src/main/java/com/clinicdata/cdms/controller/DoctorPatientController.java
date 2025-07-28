package com.clinicdata.cdms.controller;

import com.clinicdata.cdms.model.Patient;
import com.clinicdata.cdms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DoctorPatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/doctor/patients")
    public String viewPatients(Model model, Authentication authentication) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        model.addAttribute("doctorName", authentication.getName());
        return "doctor-patients";
    }

}
