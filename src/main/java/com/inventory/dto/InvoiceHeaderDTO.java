package com.inventory.dto;

import lombok.Data;

@Data
public class InvoiceHeaderDTO {

    String invoiceNumber;
    String customerName;
    Double grandTotal;
}
