package org.example.jobtrackerspring.model;

import jakarta.validation.constraints.NotEmpty;

public class Service {

    private String id;

    @NotEmpty
    private String date;

    @NotEmpty
    private String customer;

    private String crew;

    private String workPerformed;

    private String need;

    public Service(String id, String date, String customer, String crew, String workPerformed, String need) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.crew = crew;
        this.workPerformed = workPerformed;
        this.need = need;
    }

    public Service() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
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
}
