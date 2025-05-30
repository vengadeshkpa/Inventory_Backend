package com.inventory.repository;

import com.inventory.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // addCustomer and getAllCustomers are provided by JpaRepository:
    // save(Customer customer) and findAll()
}