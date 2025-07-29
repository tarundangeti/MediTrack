package com.clinicdata.cdms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clinicdata.cdms.model.Patient;
import com.clinicdata.cdms.repository.PatientRepository;
import com.clinicdata.cdms.repository.UserRepository;
import com.clinicdata.cdms.service.PatientService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientService patientService;

    // List patients
    @GetMapping
    public String listPatients(@RequestParam(value = "keyword", required = false) String keyword,
                               Authentication authentication,
                               @RequestParam(name = "from", required = false) String from,
                               Model model) {
    	
    	
        List<Patient> patients;
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("DOCTOR")) {
            String doctorUsername = authentication.getName();
            patients = patientService.getPatientsByDoctor(doctorUsername);
        } else if (role.equals("DATA_ENTRY")) {
            String dataEntryUsername = authentication.getName();
            patients = patientService.getPatientsByDataEntry(dataEntryUsername);
        } else if (keyword != null && !keyword.isEmpty()) {
            patients = patientService.searchPatientsByName(keyword);
        } else {
            patients = patientService.getAllPatients();
        }

        model.addAttribute("patients", patients);
        model.addAttribute("keyword", keyword);
        model.addAttribute("from", from);
        return "patient-list";
    }

}
