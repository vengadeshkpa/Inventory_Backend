package com.inventory.dto;

import lombok.Data;

@Data
public class StockUpdateRequest {
    private String productName;
    private String color;
    private String operationType; // PROCUREMENT, SALE, ON_HOLD
    private double yards;
    private double pieces;
}
