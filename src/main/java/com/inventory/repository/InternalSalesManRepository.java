package com.inventory.repository;

import com.inventory.model.Customer;
import com.inventory.model.InternalSalesMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalSalesManRepository  extends JpaRepository<InternalSalesMan, Long> {
}
