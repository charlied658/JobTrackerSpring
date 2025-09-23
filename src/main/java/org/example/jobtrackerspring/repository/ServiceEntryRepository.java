package org.example.jobtrackerspring.repository;

import org.example.jobtrackerspring.model.ServiceEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {}