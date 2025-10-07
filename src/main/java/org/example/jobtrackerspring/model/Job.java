package org.example.jobtrackerspring.model;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "jobs")
public class Job{
    @Id @GeneratedValue
    private String id;
    private String lossType;
    private String status;
    private String description;
    private Customer customer = new Customer();
    private List<ServiceEntry> services = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLossType() {
        return lossType;
    }

    public void setLossType(String lossType) {
        this.lossType = lossType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ServiceEntry> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntry> services) {
        this.services = services;
    }

    public LocalDate getLatestServiceDate() {
        return services.stream()
                .map(ServiceEntry::getDate)
                .max(LocalDate::compareTo)
                .orElse(null);
    }
}