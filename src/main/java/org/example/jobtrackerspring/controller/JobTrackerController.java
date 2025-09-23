package org.example.jobtrackerspring.controller;

import jakarta.validation.Valid;
import org.example.jobtrackerspring.model.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class JobTrackerController {

    private Map<String, Service> db = new HashMap<>() {{
        put("1", new Service("1","9/22/2025","John Doe","Laura C.","Applied disinfectant","Done"));
    }};

    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/services")
    public Collection<Service> get() {
        return db.values();
    }

    @GetMapping("/services/{id}")
    public Service get(@PathVariable String id) {
        Service service = db.get(id);
        if (service == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return service;
    }

    @DeleteMapping("/services/{id}")
    public void delete(@PathVariable String id) {
        Service service = db.remove(id);
        if (service == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/services")
    public ResponseEntity<Service> create(@RequestBody @Valid Service service) {
        service.setId(UUID.randomUUID().toString());
        db.put(service.getId(), service);
        return new ResponseEntity<>(service, HttpStatus.CREATED);
    }
}
