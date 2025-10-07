package org.example.jobtrackerspring.repository;

import org.example.jobtrackerspring.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByCustomerName(String name);
}
