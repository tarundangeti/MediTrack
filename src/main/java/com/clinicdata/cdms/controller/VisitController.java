package com.clinicdata.cdms.controller;

import com.clinicdata.cdms.model.Patient;
import com.clinicdata.cdms.model.Visit;
import com.clinicdata.cdms.repository.PatientRepository;
import com.clinicdata.cdms.repository.UserRepository;
import com.clinicdata.cdms.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/patients/{patientId}/visits")
public class VisitController {

    @Autowired
    private VisitService visitService;
    


    @Autowired
    private PatientRepository patientRepository;

    // ✅ View all visits for a patient
    @GetMapping
    public String viewVisits(@PathVariable Long patientId, Model model) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return "redirect:/patients";
        }

        List<Visit> visits = visitService.getVisitsByPatient(patient);
        model.addAttribute("patient", patient);
        model.addAttribute("visits", visits);
        return "visit-list";
    }

    // ✅ Show form to add a new visit
    @GetMapping("/add")
    public String showAddForm(@PathVariable Long patientId, Authentication authentication, Model model) {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            // Patient not found, redirect to patient list
            return "redirect:/patients";
        }

        // Get user role - assuming single authority
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if ("DATA_ENTRY".equals(role)|| "DOCTOR".equals(role)) {
            // User has DATA_ENTRY role, create new Visit and add to model
            Visit visit = new Visit();
            visit.setVisitDate(LocalDate.now());
            visit.setPatient(patient);
            model.addAttribute("visit", visit);
            return "visit-form";
        } else {
            // Set flag that user does not have access
            model.addAttribute("noAccess", true);
            // It’s good to add an empty 'visit' to avoid exceptions in Thymeleaf
            // Alternatively, if you want to disable the form or hide it, add another flag
            List<Visit> visits = visitService.getVisitsByPatient(patient);
            model.addAttribute("patient", patient);
            model.addAttribute("visits", visits);
            return "visit-list";
        }
        
    }


    // ✅ Save submitted visit form
    @PostMapping("/save")
    public String saveVisit(@PathVariable Long patientId,
                            @ModelAttribute("visit") Visit visit,
                            Authentication authentication) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return "redirect:/patients";
        }

        visit.setPatient(patient);
        visit.setRecordedBy(authentication.getName());
        visitService.saveVisit(visit);

        return "redirect:/patients/" + patientId + "/visits";
    }
}
