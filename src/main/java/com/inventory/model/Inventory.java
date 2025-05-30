package com.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private MasterProduct product;

    @Column(nullable = false)
    private String color;

    @Column
    private String inventoryUnit;

    @Column(nullable = false)
    private double yardAvailable;

    @Column(nullable = false)
    private double pieceAvailable;

    @Column(nullable = false)
    private double yardsOnHold = 0;

    @Column(nullable = false)
    private double piecesOnHold = 0;

    @Column(nullable = false)
    private double loadedYards = 0;

    @Column(nullable = false)
    private double loadedPieces = 0;

    @Column(nullable = false)
    private double procurementYards = 0;

    @Column(nullable = false)
    private double procurementPieces = 0;

    @Column(nullable = false)
    private double saleYards = 0;

    @Column(nullable = false)
    private double salePieces = 0;

}