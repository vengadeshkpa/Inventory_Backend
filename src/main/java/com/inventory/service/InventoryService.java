package com.inventory.service;

import com.inventory.model.Inventory;
import com.inventory.model.MasterProduct;
import com.inventory.repository.InventoryRepository;
import com.inventory.repository.MasterProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MasterProductRepository masterProductRepository;

    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getItemById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Inventory addItem(String productName, Inventory item) {
        // Find or create the product in MasterProduct table
        MasterProduct product = masterProductRepository.findByProductName(productName)
                .orElseGet(() -> {
                    MasterProduct newProduct = new MasterProduct();
                    newProduct.setProductName(productName);
                    return masterProductRepository.save(newProduct);
                });

        item.setProduct(product);
        return inventoryRepository.save(item);
    }

    public Inventory updateItem(Long id, String productName, Inventory itemDetails) {
        Optional<Inventory> optionalItem = inventoryRepository.findById(id);
        if (optionalItem.isPresent()) {
            Inventory item = optionalItem.get();

            // Find or create the product in MasterProduct table
            MasterProduct product = masterProductRepository.findByProductName(productName)
                    .orElseGet(() -> {
                        MasterProduct newProduct = new MasterProduct();
                        newProduct.setProductName(productName);
                        return masterProductRepository.save(newProduct);
                    });

            item.setProduct(product);
            item.setColor(itemDetails.getColor());
            item.setCategory(itemDetails.getCategory());
            item.setYardAvailable(itemDetails.getYardAvailable());
            item.setPieceAvailable(itemDetails.getPieceAvailable());
            return inventoryRepository.save(item);
        }
        return null;
    }

    public Inventory updateItemCond(Long id, String productName, Inventory itemDetails, String cond) {
        Optional<Inventory> optionalItem = inventoryRepository.findById(id);
        if (optionalItem.isPresent()) {
            Inventory item = optionalItem.get();

            // Find or create the product in MasterProduct table
            MasterProduct product = masterProductRepository.findByProductName(productName)
                    .orElseGet(() -> {
                        MasterProduct newProduct = new MasterProduct();
                        newProduct.setProductName(productName);
                        return masterProductRepository.save(newProduct);
                    });

            item.setProduct(product);
            item.setColor(itemDetails.getColor());
            item.setCategory(itemDetails.getCategory());
            //http://localhost:8080/api/inventory/updatecond/5/Procure
            if(cond.equals("Procure"))
            {
                double yard = item.getYardAvailable()+itemDetails.getYardAvailable();
                double piece = item.getPieceAvailable() + itemDetails.getPieceAvailable();
                item.setYardAvailable(yard);
                item.setPieceAvailable(piece);
                double procureYards = item.getProcurementYards()+itemDetails.getYardAvailable();
                double procurePieces = item.getProcurementPieces()+itemDetails.getPieceAvailable();
                item.setProcurementYards(procureYards);
                item.setProcurementPieces(procurePieces);
            }
            //http://localhost:8080/api/inventory/updatecond/5/Sales
            else if (cond.equals("Sales")) {
                double yard = item.getYardAvailable()-itemDetails.getYardAvailable();
                double piece = item.getPieceAvailable() - itemDetails.getPieceAvailable();
                item.setYardAvailable(yard);
                item.setPieceAvailable(piece);
                double saleYards = item.getSaleYards()+itemDetails.getYardAvailable();
                double salePieces = item.getSalePieces()+itemDetails.getPieceAvailable();
                item.setSaleYards(saleYards);
                item.setSalePieces(salePieces);
            }
            //http://localhost:8080/api/inventory/updatecond/5/Hold
            else if (cond.equals("Hold")) {
                double yard = item.getYardAvailable()-itemDetails.getYardAvailable();
                double piece = item.getPieceAvailable() - itemDetails.getPieceAvailable();
                item.setYardAvailable(yard);
                item.setPieceAvailable(piece);
                item.setPiecesOnHold(itemDetails.getPieceAvailable());
                item.setYardsOnHold(itemDetails.getYardAvailable());
            } else {
                item.setYardAvailable(itemDetails.getYardAvailable());
                item.setPieceAvailable(itemDetails.getPieceAvailable());
            }

            return inventoryRepository.save(item);
        }
        return null;
    }

    public void deleteItem(Long id) {
        inventoryRepository.deleteById(id);
    }
}