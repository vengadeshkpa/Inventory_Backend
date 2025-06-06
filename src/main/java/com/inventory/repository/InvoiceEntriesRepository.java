package com.inventory.repository;

import com.inventory.model.InvoiceEntries;
import com.inventory.model.InvoiceHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceEntriesRepository extends JpaRepository<InvoiceEntries, Long> {

    List<InvoiceEntries> findByInvoiceHeader(InvoiceHeader invoiceHeader);
}