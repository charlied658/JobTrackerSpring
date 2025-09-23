package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.repository.JobRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

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
}