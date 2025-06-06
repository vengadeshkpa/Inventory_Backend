package com.inventory.dto;

import com.inventory.dto.InvoiceHeaderDTO;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceRequest {
    private InvoiceHeaderDTO invoiceHeader;
    private List<InvoiceEntriesDTO> invoiceEntries;

}