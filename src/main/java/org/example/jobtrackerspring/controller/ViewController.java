package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.model.ServiceDisplay;
import org.example.jobtrackerspring.model.ServiceEntry;
import org.example.jobtrackerspring.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private JobRepository jobRepo;

    // === All Jobs Page ===
    @GetMapping("/jobs")
    public String listJobs(Model model) {
        List<Job> jobs = jobRepo.findAll();
        // Optionally sort by latest service date:
        jobs.sort((a, b) -> {
            LocalDate adate = a.getServices().isEmpty() ? LocalDate.MIN
                    : a.getServices().get(a.getServices().size() - 1).getDate();
            LocalDate bdate = b.getServices().isEmpty() ? LocalDate.MIN
                    : b.getServices().get(b.getServices().size() - 1).getDate();
            return bdate.compareTo(adate);
        });
        model.addAttribute("jobs", jobs);
        return "jobs";
    }


    // === Job Detail Page ===
    @GetMapping("/jobs/{id}")
    public String viewJobDetails(@PathVariable String id, Model model) {
        Job job = jobRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found: " + id));
        model.addAttribute("job", job);
        model.addAttribute("services", job.getServices()); // no separate lookup needed
        return "job-detail";
    }

    @GetMapping("/services")
    public String viewServices(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model
    ) {
        if (date == null) {
            date = LocalDate.now();
        }

        LocalDate finalDate = date;

        List<Job> allJobs = jobRepo.findAll();

        // Flatten services from all jobs, attaching job info
        List<ServiceDisplay> servicesForDate = allJobs.stream()
                .flatMap(job -> job.getServices().stream()
                        .filter(s -> finalDate.equals(s.getDate()))
                        .map(s -> new ServiceDisplay(job.getId(), job.getCustomer().getName(), s)))
                .collect(Collectors.toList());

        model.addAttribute("date", date);
        model.addAttribute("previousDate", date.minusDays(1));
        model.addAttribute("nextDate", date.plusDays(1));
        model.addAttribute("services", servicesForDate);
        return "services";
    }

    // === Add Job (Modal Form) ===
    @PostMapping("/jobs/add")
    public String addJob(@ModelAttribute Job job) {
        jobRepo.save(job);
        return "redirect:/view/jobs";
    }

    // === Add Service to Job ===
    @PostMapping("/jobs/{id}/addService")
    public String addService(@PathVariable String id, @ModelAttribute ServiceEntry service) {
        Job job = jobRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found: " + id));

        job.getServices().add(service);
        jobRepo.save(job); // stores updated embedded list

        return "redirect:/view/jobs/" + id;
    }

    @PostMapping("/services/edit")
    public String editService(
            @PathVariable String jobId,
            @PathVariable int index,
            @ModelAttribute ServiceEntry updatedService
    ) {
        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found: " + jobId));

        if (index < 0 || index >= job.getServices().size()) {
            throw new IllegalArgumentException("Invalid service index for job " + jobId);
        }

        // Replace the old service entry with the new one
        job.getServices().set(index, updatedService);
        jobRepo.save(job);

        return "redirect:/view/jobs/" + jobId;
    }

    // === Edit Job ===
    @PostMapping("/jobs/edit")
    public String editJob(@ModelAttribute Job updatedJob) {
        Job existingJob = jobRepo.findById(updatedJob.getId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found: " + updatedJob.getId()));
        updatedJob.setServices(existingJob.getServices());
        jobRepo.save(updatedJob); // MongoDB auto-replaces matching ID
        return "redirect:/view/jobs/" + updatedJob.getId();
    }

    // === Delete Job ===
    @PostMapping("/jobs/{id}/delete")
    public String deleteJob(@PathVariable String id) {
        jobRepo.deleteById(id);
        return "redirect:/view/jobs";
    }
}
