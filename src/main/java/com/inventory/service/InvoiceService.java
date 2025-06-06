package com.inventory.service;

import com.inventory.dto.InvoiceEntriesDTO;
import com.inventory.dto.InvoiceHeaderDTO;
import com.inventory.model.InvoiceEntries;
import com.inventory.model.InvoiceHeader;
import com.inventory.repository.InvoiceEntriesRepository;
import com.inventory.repository.InvoiceHeaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceService {

    @Autowired
    private InvoiceHeaderRepository invoiceHeaderRepository;

    @Autowired
    private InvoiceEntriesRepository invoiceEntriesRepository;

    public void saveInvoice(InvoiceHeaderDTO iHeader, List<InvoiceEntriesDTO> iEntries) {
        // Save the invoice header

        List<InvoiceEntries> entries = new ArrayList<>();

        InvoiceHeader savedHeader = new InvoiceHeader();
        savedHeader.setInvoiceNumber(iHeader.getInvoiceNumber());
        savedHeader.setCustomerName(iHeader.getCustomerName());
        savedHeader.setTotalPrice(iHeader.getGrandTotal());

        invoiceHeaderRepository.save(savedHeader);

        log.info("Saved invoice header: {}", savedHeader);

        iEntries.stream()
                .map(entry -> {
                    InvoiceEntries invoiceEntry = new InvoiceEntries();
                    invoiceEntry.setMasterProduct(entry.getProductName());
                    invoiceEntry.setColor(entry.getColor());
                    invoiceEntry.setTotalQuantity(entry.getQuantity());
                    invoiceEntry.setPerUnitPrice(entry.getUnitPrice());
                    invoiceEntry.setTotalProductPrice(entry.getTotalProductPrice());
                    invoiceEntry.setNumPieces(entry.getPieceAvailable());
                    invoiceEntry.setInvoiceHeader(savedHeader);
                    log.info("Creating invoice entry: {}", invoiceEntry);
                    entries.add(invoiceEntry);
                    return invoiceEntry;
                })
                .forEach(invoiceEntriesRepository::save);

        log.info("Saved invoice entries: {}", entries);

        savedHeader.setEntries(entries);

        log.info("Updating invoice header with entries: {}", savedHeader);

        invoiceHeaderRepository.save(savedHeader);
    }

    public List<InvoiceHeader> getAllInvoiceHeaders() {
        return invoiceHeaderRepository.findAll();
    }

    public List<InvoiceEntries> getInvoiceEntriesByHeaderId(String invoiceHeaderId) {
        log.info("Fetching invoice entries for header ID: {}", invoiceHeaderId);
        InvoiceHeader invoiceHeader = invoiceHeaderRepository.findByInvoiceNumber(invoiceHeaderId)
                .orElseThrow(() -> new RuntimeException("InvoiceHeader not found for Invoice Number: " + invoiceHeaderId));
        return invoiceEntriesRepository.findByInvoiceHeader(invoiceHeader);
    }
}