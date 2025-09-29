package org.example.jobtrackerspring.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ServiceEntry {
    @Id @GeneratedValue
    private Long id;
    private LocalDate date;
    private String crew;
    private String serviceType;
    private String instructions;
    private String workPerformed;
    private String need;

    @ManyToOne
    private Job job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getWorkPerformed() {
        return workPerformed;
    }

    public void setWorkPerformed(String workPerformed) {
        this.workPerformed = workPerformed;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
