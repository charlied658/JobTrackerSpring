package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.model.Customer;
import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.repository.CustomerRepository;
import org.example.jobtrackerspring.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JobController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private JobRepository jobRepo;

    @PostMapping("/jobs/add")
    public String addJob(@RequestParam Long customerId,
                         @RequestParam String lossType,
                         @RequestParam(required = false) String description) {
        Customer customer = customerRepo.findById(customerId).orElseThrow();
        Job job = new Job();
        job.setCustomer(customer);
        job.setLossType(lossType);
        job.setDescription(description);
        job.setStatus("Active");
        job.setAddress(customer.getAddress());
        jobRepo.save(job);
        return "redirect:/view/customers/" + customerId;
    }

    @PostMapping("/jobs/edit")
    public String editJob(@RequestParam Long id,
                          @RequestParam String lossType,
                          @RequestParam String status,
                          @RequestParam String address,
                          @RequestParam String description) {
        Job job = jobRepo.findById(id).orElseThrow();

        job.setLossType(lossType);
        job.setStatus(status);
        job.setAddress(address);
        job.setDescription(description);
        jobRepo.save(job);

        return "redirect:/view/jobs/" + id;
    }
}
