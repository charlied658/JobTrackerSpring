package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.repository.JobRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ViewController {

    private final JobRepository jobRepo;

    public ViewController(JobRepository jobRepo) {
        this.jobRepo = jobRepo;
    }

    @GetMapping("/view/jobs")
    public String viewJobs(Model model) {
        model.addAttribute("jobs", jobRepo.findAll());
        return "jobs"; // resolves to templates/jobs.html
    }

    @GetMapping("/jobs/{id}")
    public String viewJobDetail(@PathVariable Long id, Model model) {
        Optional<Job> jobOpt = jobRepo.findById(id);
        if (jobOpt.isEmpty()) {
            return "redirect:/view/jobs"; // or a 404 page
        }

        model.addAttribute("job", jobOpt.get());
        return "job-detail"; // maps to job-detail.html
    }
}