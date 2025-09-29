package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.model.Customer;
import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.model.ServiceEntry;
import org.example.jobtrackerspring.repository.CustomerRepository;
import org.example.jobtrackerspring.repository.JobRepository;
import org.example.jobtrackerspring.repository.ServiceEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private ServiceEntryRepository serviceRepo;

    // 📋 View all customers as a simple list with search
    @GetMapping("/customers")
    public String viewCustomers(Model model) {
        List<Customer> customers = customerRepo.findAll();
        model.addAttribute("customers", customers);
        return "customers"; // thymeleaf template: customer_list.html
    }

    @GetMapping("/customers/{id}")
    public String viewCustomerDetails(@PathVariable Long id, Model model) {
        Optional<Customer> customerOpt = customerRepo.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            model.addAttribute("customer", customer);
            model.addAttribute("jobs", jobRepo.findByCustomer(customer));
            return "customer-detail";  // This will be the name of the new HTML file
        } else {
            return "redirect:/view/customers"; // Fallback if not found
        }
    }

    @GetMapping("/jobs")
    public String viewJobs(Model model) {
        List<Job> jobs = jobRepo.findAll();
        model.addAttribute("jobs", jobs);
        return "jobs"; // This matches the name of the HTML file: jobs.html
    }

    @GetMapping("/jobs/{id}")
    public String viewJobDetail(@PathVariable Long id, Model model) {
        Job job = jobRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job ID: " + id));
        model.addAttribute("job", job);
        return "job-detail";
    }

    @GetMapping("/services")
    public String viewServices(Model model) {
        List<ServiceEntry> allServices = serviceRepo.findAll(Sort.by(Sort.Direction.DESC, "date"));

        Map<LocalDate, List<ServiceEntry>> grouped = allServices.stream()
                .collect(Collectors.groupingBy(ServiceEntry::getDate, LinkedHashMap::new, Collectors.toList()));

        model.addAttribute("groupedServices", grouped);
        return "services";
    }

    @GetMapping("/services/{id}")
    public String viewServiceDetail(@PathVariable Long id, Model model) {
        ServiceEntry service = serviceRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid service ID: " + id));
        model.addAttribute("service", service);
        return "service-detail";
    }

}