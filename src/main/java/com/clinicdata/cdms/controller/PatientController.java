package com.clinicdata.cdms.controller;

import com.clinicdata.cdms.model.Patient;
import com.clinicdata.cdms.model.User;
import com.clinicdata.cdms.repository.PatientRepository;
import com.clinicdata.cdms.repository.UserRepository;
import com.clinicdata.cdms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

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

    // Show form to add patient
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient-form";
    }

    // Save new or updated patient
    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient,
                              Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("DOCTOR") || role.equals("DATA_ENTRY")) {
            patient.setAssignedDoctor(username);
        }

        patientService.savePatient(patient);
        return "redirect:/patients";
    }

    // Edit patient form
    @GetMapping("/edit/{id}")
    public String editPatient(@PathVariable Long id,
                              Authentication authentication,
                              Model model) {
        Patient patient = patientService.getPatientById(id);
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (patient == null) {
            return "redirect:/patients";
        }

        if (role.equals("DOCTOR") && !patient.getAssignedDoctor().equals(authentication.getName())) {
            return "redirect:/patients?error=unauthorized";
        }

        model.addAttribute("patient", patient);
        return "patient-form";
    }

    // Delete patient (Admin only)
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id,
                                Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            patientService.deletePatientById(id);
        }
        return "redirect:/patients";
    }

    // View patient details
    @GetMapping("/view/{id}")
    public String viewPatient(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            return "redirect:/patients";
        }
        model.addAttribute("patient", patient);
        return "patient-detail";
    }

    // Admin assigns patient to data entry
    @GetMapping("/{id}/assign")
    public String showAssignForm(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id).orElse(null);
        List<User> dataEntryUsers = userRepository.findByRole("DATA_ENTRY");

        model.addAttribute("patient", patient);
        model.addAttribute("dataEntryUsers", dataEntryUsers);
        return "assign-patient";
    }

    @PostMapping("/{id}/assign")
    public String assignPatient(@PathVariable Long id, @RequestParam Long userId) {
        Patient patient = patientRepository.findById(id).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (patient != null && user != null) {
            patient.setAssignedTo(user);
            patientRepository.save(patient);
        }
        return "redirect:/patients";
    }
    
    @GetMapping("/dataentry")
    @PreAuthorize("hasRole('DATA_ENTRY')")
    public String dataentryPatient(@RequestParam(name = "from", required = false) String from,Authentication authentication, Model model) {
        String dataEntryUsername = authentication.getName();
        List<Patient> patients = patientService.getPatientsByDataEntry(dataEntryUsername);

        if (patients == null) {
            return "redirect:/patients";
        }
        model.addAttribute("dataEntryUsername",dataEntryUsername);
        model.addAttribute("patients", patients);
        model.addAttribute("from", from);
        return "dataentry-patient";
    }

}
