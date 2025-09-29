package org.example.jobtrackerspring.controller;

import org.example.jobtrackerspring.model.Customer;
import org.example.jobtrackerspring.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    @PostMapping("/customers/add")
    public String addCustomer(@RequestParam String name,
                              @RequestParam(required = false) String business,
                              @RequestParam(required = false) String phone,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String address) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setBusiness(business);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);
        customerRepo.save(customer);
        return "redirect:/view/customers";
    }

    @PostMapping("/customers/edit")
    public String editCustomer(@RequestParam Long id,
                               @RequestParam String name,
                               @RequestParam String business,
                               @RequestParam String phone,
                               @RequestParam String email,
                               @RequestParam String address) {
        Customer customer = customerRepo.findById(id).orElseThrow();
        customer.setName(name);
        customer.setBusiness(business);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);
        customerRepo.save(customer);
        return "redirect:/view/customers/" + id;
    }
}
