package com.clinicdata.cdms.controller;

import com.clinicdata.cdms.model.LabResult;
import com.clinicdata.cdms.model.Patient;
import com.clinicdata.cdms.model.Visit;
import com.clinicdata.cdms.repository.LabResultRepository;
import com.clinicdata.cdms.repository.PatientRepository;
import com.clinicdata.cdms.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/visits")
public class LabResultController {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private LabResultRepository labResultRepository;

    @Autowired
    private PatientRepository patientRepository;

    // ✅ View lab results for a visit
    @GetMapping("/{visitId}/lab-results")
    public String viewLabResults(@PathVariable Long visitId,
                                 Authentication authentication,
                                 Model model) {
        Visit visit = visitRepository.findById(visitId).orElse(null);
        if (visit == null) {
            return "redirect:/patients";
        }

        Patient patient = visit.getPatient();

        // Restrict access if the patient is not assigned to the current data entry user
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("DATA_ENTRY"))) {
            if (patient.getAssignedTo() == null || !authentication.getName().equals(patient.getAssignedTo().getUsername())) {
                return "redirect:/patients?error=unauthorized";
            }
        }

        List<LabResult> labResults = labResultRepository.findByVisit(visit);
        model.addAttribute("visit", visit);
        model.addAttribute("labResults", labResults);
        return "labresult-list";
    }

    // ✅ Show add lab result form (DATA_ENTRY only)
    @GetMapping("/{visitId}/lab-results/add")
    public String showAddForm(@PathVariable Long visitId,
                              Authentication authentication,
                              Model model) {
        Visit visit = visitRepository.findById(visitId).orElse(null);
        if (visit == null) {
            return "redirect:/patients";
        }

        Patient patient = visit.getPatient();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        // Restrict to assigned data entry user
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("DATA_ENTRY"))) {
            if (patient.getAssignedTo() == null || !authentication.getName().equals(patient.getAssignedTo().getUsername())) {
                return "redirect:/patients?error=unauthorized";
            }
        }
        if ("DATA_ENTRY".equals(role) || "DOCTOR".equals(role)) {
        LabResult labResult = new LabResult();
        labResult.setVisit(visit);
        model.addAttribute("labResult", labResult);
        return "labresult-form";
        } else {
            List<LabResult> labResults = labResultRepository.findByVisit(visit);
            model.addAttribute("noAccess", true);
            model.addAttribute("visit", visit);
            model.addAttribute("labResults", labResults);
            return "labresult-list";
        	
        }
    }

    // ✅ Save lab result (DATA_ENTRY only)
    @PostMapping("/{visitId}/lab-results/save")
    public String saveLabResult(@PathVariable Long visitId,
                                @ModelAttribute LabResult labResult,
                                Authentication authentication) {
        Visit visit = visitRepository.findById(visitId).orElse(null);
        if (visit == null) {
            return "redirect:/patients";
        }

        Patient patient = visit.getPatient();

        // Restrict to assigned data entry user
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("DATA_ENTRY"))) {
            if (patient.getAssignedTo() == null || !authentication.getName().equals(patient.getAssignedTo().getUsername())) {
                return "redirect:/patients?error=unauthorized";
            }
        }

        labResult.setVisit(visit);
        labResult.setRecordedBy(authentication.getName());
        labResultRepository.save(labResult);

        return "redirect:/visits/" + visitId + "/lab-results";
    }
}
