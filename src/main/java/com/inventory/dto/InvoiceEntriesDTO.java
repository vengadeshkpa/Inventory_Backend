package com.inventory.dto;

import lombok.Data;

@Data
public class InvoiceEntriesDTO {
    String color;
    String productName;
    Integer pieceAvailable;
    double quantity;
    double totalProductPrice;
    double unitPrice;
}
