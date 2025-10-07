package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.model.ServiceEntry;
import org.example.jobtrackerspring.repository.JobRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepo;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Job> getJobById(@PathVariable String id) {
        return jobRepo.findById(id);
    }

    @PostMapping
    public Job addJob(@RequestBody Job job) {
        return jobRepo.save(job);
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable String id, @RequestBody Job updated) {
        return jobRepo.findById(id)
                .map(job -> {
                    job.getCustomer().setName(updated.getCustomer().getName());
                    job.setStatus(updated.getStatus());
                    job.setLossType(updated.getLossType());
                    job.setServices(updated.getServices());
                    return jobRepo.save(job);
                })
                .orElseGet(() -> {
                    updated.setId(id);
                    return jobRepo.save(updated);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable String id) {
        jobRepo.deleteById(id);
    }

    @PatchMapping("/{id}/addService")
    public Job addServiceToJob(@PathVariable String id, @RequestBody ServiceEntry newService) {
        Job job = jobRepo.findById(id).orElseThrow();
        job.getServices().add(newService);
        return jobRepo.save(job);
    }
}
