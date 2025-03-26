package com.inventory.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String category;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MasterProduct getProduct() {
        return product;
    }

    public void setProduct(MasterProduct product) {
        this.product = product;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getYardAvailable() {
        return yardAvailable;
    }

    public void setYardAvailable(double yardAvailable) {
        this.yardAvailable = yardAvailable;
    }

    public double getPieceAvailable() {
        return pieceAvailable;
    }

    public void setPieceAvailable(double pieceAvailable) {
        this.pieceAvailable = pieceAvailable;
    }

    public double getYardsOnHold() {
        return yardsOnHold;
    }

    public void setYardsOnHold(double yardsOnHold) {
        this.yardsOnHold = yardsOnHold;
    }

    public double getPiecesOnHold() {
        return piecesOnHold;
    }

    public void setPiecesOnHold(double piecesOnHold) {
        this.piecesOnHold = piecesOnHold;
    }

    public double getLoadedYards() {
        return loadedYards;
    }

    public void setLoadedYards(double loadedYards) {
        this.loadedYards = loadedYards;
    }

    public double getLoadedPieces() {
        return loadedPieces;
    }

    public void setLoadedPieces(double loadedPieces) {
        this.loadedPieces = loadedPieces;
    }

    public double getProcurementYards() {
        return procurementYards;
    }

    public void setProcurementYards(double procurementYards) {
        this.procurementYards = procurementYards;
    }

    public double getProcurementPieces() {
        return procurementPieces;
    }

    public void setProcurementPieces(double procurementPieces) {
        this.procurementPieces = procurementPieces;
    }

    public double getSaleYards() {
        return saleYards;
    }

    public void setSaleYards(double saleYards) {
        this.saleYards = saleYards;
    }

    public double getSalePieces() {
        return salePieces;
    }

    public void setSalePieces(double salePieces) {
        this.salePieces = salePieces;
    }
}