package org.example.jobtrackerspring.repository;

import org.example.jobtrackerspring.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {}