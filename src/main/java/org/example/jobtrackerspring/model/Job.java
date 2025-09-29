package org.example.jobtrackerspring.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Job {
    @Id @GeneratedValue
    private Long id;
    private String lossType;
    private String status;
    private String address;
    private String description;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<ServiceEntry> services = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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