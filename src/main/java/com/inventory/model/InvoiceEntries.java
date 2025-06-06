package com.inventory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INVOICE_ENTRIES")
public class InvoiceEntries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    @JsonBackReference
    private InvoiceHeader invoiceHeader;

    @Column(name = "MASTER_PRODUCT", nullable = false)
    private String masterProduct;

    @Column(name = "COLOR", nullable = false)
    private String color;

    @Column(name = "NUM_PIECES", nullable = false)
    private Integer numPieces;

    @Column(name = "TOTAL_QUANTITY", nullable = false)
    private Double totalQuantity;

    @Column(name = "PER_UNIT_PRICE", nullable = false)
    private Double perUnitPrice;

    @Column(name = "TOTAL_PRODUCT_PRICE", nullable = false)
    private Double totalProductPrice;
}