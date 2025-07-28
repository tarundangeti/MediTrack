package com.clinicdata.cdms.model;

import jakarta.persistence.*;

@Entity
public class LabResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;

    @Column(length = 1000)
    private String result;

    private String recordedBy;

    @ManyToOne
    @JoinColumn(name = "visit_id")
    private Visit visit;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getRecordedBy() { return recordedBy; }
    public void setRecordedBy(String recordedBy) { this.recordedBy = recordedBy; }

    public Visit getVisit() { return visit; }
    public void setVisit(Visit visit) { this.visit = visit; }
}
