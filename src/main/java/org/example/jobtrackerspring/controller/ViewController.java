package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.model.ServiceEntry;
import org.example.jobtrackerspring.repository.JobRepository;
import org.example.jobtrackerspring.repository.ServiceEntryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private final JobRepository jobRepo;
    private final ServiceEntryRepository serviceRepo;

    public ViewController(JobRepository jobRepo, ServiceEntryRepository serviceRepo) {
        this.jobRepo = jobRepo;
        this.serviceRepo = serviceRepo;
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

    @GetMapping("/view/scheduler")
    public String viewScheduler(Model model) {
        List<ServiceEntry> allServices = serviceRepo.findAll();

        // Group services by date (LocalDate only, no time)
        Map<LocalDate, List<ServiceEntry>> groupedByDate = allServices.stream()
                .collect(Collectors.groupingBy(ServiceEntry::getDate));

        // Sort by most recent date first
        Map<LocalDate, List<ServiceEntry>> sortedByDateDesc = new TreeMap<>(Comparator.reverseOrder());
        sortedByDateDesc.putAll(groupedByDate);

        model.addAttribute("groupedServices", sortedByDateDesc);
        model.addAttribute("currentPage", "scheduler");

        return "scheduler";
    }
}