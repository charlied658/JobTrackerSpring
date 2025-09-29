package org.example.jobtrackerspring.repository;

import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.model.ServiceEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {
    List<ServiceEntry> findByJob(Job job);
    List<ServiceEntry> findByDate(LocalDate date);
}