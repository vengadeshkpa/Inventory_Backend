package com.inventory.repository;

import com.inventory.model.InvoiceHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceHeaderRepository extends JpaRepository<InvoiceHeader, Long> {
    Optional<InvoiceHeader> findByInvoiceNumber(String invoiceNumber);

}