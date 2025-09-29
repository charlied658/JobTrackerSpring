package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.model.ServiceEntry;
import org.example.jobtrackerspring.repository.CustomerRepository;
import org.example.jobtrackerspring.repository.JobRepository;
import org.example.jobtrackerspring.repository.ServiceEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ServiceController {

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private ServiceEntryRepository serviceRepo;

    @PostMapping("/services/add")
    public String addService(@RequestParam Long jobId,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             @RequestParam String crew,
                             @RequestParam String serviceType,
                             @RequestParam String instructions,
                             @RequestParam String workPerformed,
                             @RequestParam String need) {
        Job job = jobRepo.findById(jobId).orElseThrow();
        ServiceEntry service = new ServiceEntry();
        service.setJob(job);
        service.setDate(date);
        service.setCrew(crew);
        service.setServiceType(serviceType);
        service.setInstructions(instructions);
        service.setWorkPerformed(workPerformed);
        service.setNeed(need);
        serviceRepo.save(service);
        return "redirect:/view/jobs/" + jobId;
    }

    @PostMapping("/services/edit")
    public String editService(@RequestParam Long id,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              @RequestParam String crew,
                              @RequestParam String serviceType,
                              @RequestParam String instructions,
                              @RequestParam String workPerformed,
                              @RequestParam String need) {

        ServiceEntry service = serviceRepo.findById(id).orElseThrow();
        service.setDate(date);
        service.setCrew(crew);
        service.setServiceType(serviceType);
        service.setInstructions(instructions);
        service.setWorkPerformed(workPerformed);
        service.setNeed(need);
        serviceRepo.save(service);

        return "redirect:/jobs/" + id; //
    }

}
