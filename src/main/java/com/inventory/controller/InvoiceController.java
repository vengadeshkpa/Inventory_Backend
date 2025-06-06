package com.inventory.controller;

import com.inventory.dto.InvoiceEntriesDTO;
import com.inventory.dto.InvoiceHeaderDTO;
import com.inventory.dto.InvoiceRequest;
import com.inventory.model.InvoiceEntries;
import com.inventory.model.InvoiceHeader;
import com.inventory.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public void saveInvoice(@RequestBody InvoiceRequest request) {

        InvoiceHeaderDTO iHeader = request.getInvoiceHeader();
        List<InvoiceEntriesDTO> iEntries = request.getInvoiceEntries();

        log.info("Saving invoice with header: {}", iHeader);
        log.info("Saving invoice with entries: {}", iEntries);

        invoiceService.saveInvoice(iHeader, iEntries);

    }

    @GetMapping("/getAllHeaders")
    public List<InvoiceHeader> getAllInvoiceHeaders() {
        log.info("Fetching all invoice headers");
        return invoiceService.getAllInvoiceHeaders();
    }

    @GetMapping("/{invoiceHeaderId}/entries")
    public List<InvoiceEntries> getInvoiceEntriesByHeaderId(@PathVariable String invoiceHeaderId) {
        log.info("Fetching invoice entries for header ID: {}", invoiceHeaderId);
        return invoiceService.getInvoiceEntriesByHeaderId(invoiceHeaderId);
    }


}