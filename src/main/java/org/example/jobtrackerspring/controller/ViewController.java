package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.model.ServiceEntry;
import org.example.jobtrackerspring.repository.JobRepository;
import org.example.jobtrackerspring.repository.ServiceEntryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
    public String viewJobs(@RequestParam(required = false) String search, Model model) {
        List<Job> jobs;

        if (search != null && !search.isEmpty()) {
            jobs = jobRepo.findByCustomerContainingIgnoreCase(search);
        } else {
            jobs = jobRepo.findAll();
        }

        model.addAttribute("jobs", jobs);
        model.addAttribute("search", search); // to keep value in form
        model.addAttribute("currentPage", "jobs");
        return "jobs";
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
    public String viewScheduler(@RequestParam(required = false)
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                LocalDate date,
                                Model model) {
        LocalDate selectedDate = (date != null) ? date : LocalDate.now();
        List<Job> allJobs = jobRepo.findAll();

        List<ServiceEntry> servicesForDate = serviceRepo.findAll().stream()
                .filter(s -> selectedDate.equals(s.getDate()))
                .collect(Collectors.toList());

        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("services", servicesForDate);
        model.addAttribute("currentPage", "scheduler");
        model.addAttribute("allJobs", allJobs);

        return "scheduler";
    }

    @PostMapping("/services/add")
    public String addService(@RequestParam Long jobId,
                             @RequestParam String crew,
                             @RequestParam String workPerformed,
                             @RequestParam String need,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        Job job = jobRepo.findById(jobId).orElse(null);
        if (job == null) {
            // Handle error (redirect with error message or log it)
            return "redirect:/view/scheduler?date=" + new SimpleDateFormat("yyyy-MM-dd").format(date);
        }

        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        ServiceEntry service = new ServiceEntry();
        service.setJob(job);
        service.setCrew(crew);
        service.setWorkPerformed(workPerformed);
        service.setNeed(need);
        service.setDate(localDate);
        serviceRepo.save(service);

        String formatted = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return "redirect:/view/scheduler?date=" + formatted;
    }

    @PostMapping("/services/update")
    public String updateService(@RequestParam Long id,
                                @RequestParam String date,
                                @RequestParam String crew,
                                @RequestParam String workPerformed,
                                @RequestParam String need) {

        Optional<ServiceEntry> optional = serviceRepo.findById(id);
        if (optional.isPresent()) {
            ServiceEntry service = optional.get();
            service.setCrew(crew);
            service.setWorkPerformed(workPerformed);
            service.setNeed(need);
            serviceRepo.save(service);
        }

        // Redirect back to scheduler with same date if you want
        return "redirect:/view/scheduler?date=" + date;
    }

    @PostMapping("/services/delete")
    public String deleteService(@RequestParam Long id,
                                @RequestParam String date) {
        serviceRepo.deleteById(id);
        return "redirect:/view/scheduler?date=" + date;
    }
}