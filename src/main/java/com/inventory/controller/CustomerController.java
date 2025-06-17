package com.inventory.controller;

import com.inventory.model.Customer;
import com.inventory.model.InternalSalesMan;
import com.inventory.repository.InternalSalesManRepository;
import com.inventory.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Autowired
    private InternalSalesManRepository internalSalesManRepository;

    @PostMapping("/addCustomer")
    public Customer addCustomer(@RequestBody Map<String, Object> request) {

        Customer customer = new Customer();
        Long internalSalesManId = request.get("internalSalesManId") != null ? Long.valueOf(request.get("internalSalesManId").toString()) : null;
        InternalSalesMan internalSalesMan = null;
        if (internalSalesManId != null) {
            internalSalesMan = internalSalesManRepository.findById(internalSalesManId).orElse(null);
        }
        customer.setInternalSalesMan(internalSalesMan);
        customer.setName((String) request.get("name"));
        customer.setCategory((String) request.get("category"));
        customer.setLocation((String) request.get("location"));
        customer.setAddressLine1((String) request.get("addressLine1"));
        customer.setAddressLine2((String) request.get("addressLine2"));
        customer.setPinCode((String) request.get("pinCode"));
        customer.setContactNumber1((String) request.get("contactNumber1"));
        customer.setContactNumber2((String) request.get("contactNumber2"));
        customer.setCustomerPOC((String) request.get("customerPOC"));
        customer.setCustomerPOCContact((String) request.get("customerPOCContact"));

        log.info("Adding customer: {}", customer);

        return service.addCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @GetMapping("/getInternalSalesmen")
    public List<InternalSalesMan> getAllInternalSalesmen() {
        return internalSalesManRepository.findAll();
    }
}