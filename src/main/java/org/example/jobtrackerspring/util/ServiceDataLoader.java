package org.example.jobtrackerspring.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.jobtrackerspring.model.Job;
import org.example.jobtrackerspring.model.ServiceEntry;
import org.example.jobtrackerspring.repository.JobRepository;
import org.example.jobtrackerspring.repository.ServiceEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ServiceDataLoader implements CommandLineRunner {

    private final JobRepository jobRepo;
    private final ServiceEntryRepository serviceRepo;
    private final ObjectMapper objectMapper;

    public ServiceDataLoader(JobRepository jobRepo, ServiceEntryRepository serviceRepo) {
        this.jobRepo = jobRepo;
        this.serviceRepo = serviceRepo;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // For LocalDate parsing
    }

    @Override
    public void run(String... args) throws Exception {
        InputStream is = getClass().getResourceAsStream("/data/services.json");
        if (is == null) {
            System.out.println("⚠️  No JSON file found at /data/services.json");
            return;
        }

        List<Map<String, String>> services = objectMapper.readValue(is, new TypeReference<>() {});
        Map<String, Job> jobCache = new HashMap<>();
        Map<String, Integer> jobNumberCount = new HashMap<>();

        for (Map<String, String> entry : services) {
            String customer = entry.get("Customer").trim();
            String crew = entry.get("Crew");
            String work = entry.get("WorkPerformed");
            String need = entry.get("Need");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            LocalDate date = LocalDate.parse(entry.get("Date").trim(), formatter);

            Job job = jobCache.computeIfAbsent(customer, c -> {
                // Generate a simple job number per customer, like JS-001
                String initials = Arrays.stream(c.split(" "))
                        .map(s -> s.substring(0, 1).toUpperCase())
                        .collect(Collectors.joining());
                int count = jobNumberCount.getOrDefault(initials, 1);
                jobNumberCount.put(initials, count + 1);

                Job newJob = new Job();
                newJob.setJobNumber(initials + "-" + String.format("%03d", count));
                newJob.setCustomer(c);
                newJob.setAddress("Unknown");  // No address in data
                newJob.setStatus("Imported");
                return jobRepo.save(newJob);
            });

            ServiceEntry service = new ServiceEntry();
            service.setJob(job);
            service.setDate(date);
            service.setCrew(crew);
            service.setWorkPerformed(work);
            service.setNeed(need);

            serviceRepo.save(service);
        }

        System.out.println("✅ Imported " + services.size() + " services grouped by customer");
    }
}
