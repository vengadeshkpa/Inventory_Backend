package com.inventory.service;

import com.inventory.model.Inventory;
import com.inventory.model.MasterProduct;
import com.inventory.repository.InventoryRepository;
import com.inventory.repository.MasterProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MasterProductRepository masterProductRepository;
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }

    public List<MasterProduct> getAllProducts() {
        return masterProductRepository.findAll();
    }

    public Long getMasterProductId(String productName) {
        return masterProductRepository.findByProductName(productName)
                .map(MasterProduct::getId)
                .orElseThrow(() -> new RuntimeException("MasterProduct not found for productName: " + productName));
    }

    public Optional<Inventory> getItemById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Inventory getItemByProductColorCategory(Long productId, String color, String category) {
        return inventoryRepository.findByProduct_IdAndColorAndCategory(productId, color, category);
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

    public MasterProduct addProduct(MasterProduct product) {
        return masterProductRepository.save(product);
    }

    public Inventory updateItem(Long id, String productName, Inventory itemDetails) {
        Optional<Inventory> optionalItem = inventoryRepository.findById(id);
        log.info("Inside updateItem ");
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
        log.info("Updating item with optionalItem : {} {} {}", optionalItem.isPresent(), id, cond);
        if (optionalItem.isPresent()) {
            Inventory item = optionalItem.get();

            log.info("Updating item with ID: {} and item: {}", id,item);

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

                log.info("inside Procure");
                double yard = item.getYardAvailable()+itemDetails.getYardAvailable();
                double piece = item.getPieceAvailable() + itemDetails.getPieceAvailable();
                item.setYardAvailable(yard);
                item.setPieceAvailable(piece);
                double procureYards = item.getProcurementYards()+itemDetails.getYardAvailable();
                double procurePieces = item.getProcurementPieces()+itemDetails.getPieceAvailable();
                item.setProcurementYards(procureYards);
                item.setProcurementPieces(procurePieces);
                log.info("inside Procure - {}",item);
            }
            //http://localhost:8080/api/inventory/updatecond/5/Sales
            else if (cond.equals("Sales")) {

                log.info("inside Sales");
                double yard = item.getYardAvailable()-itemDetails.getYardAvailable();
                double piece = item.getPieceAvailable() - itemDetails.getPieceAvailable();
                item.setYardAvailable(yard);
                item.setPieceAvailable(piece);
                double saleYards = item.getSaleYards()+itemDetails.getYardAvailable();
                double salePieces = item.getSalePieces()+itemDetails.getPieceAvailable();
                item.setSaleYards(saleYards);
                item.setSalePieces(salePieces);
                log.info("inside Sales - {}",item);
            }
            //http://localhost:8080/api/inventory/updatecond/5/Hold
            else if (cond.equals("Hold")) {
                log.info("inside Hold");
                double yard = item.getYardAvailable()-itemDetails.getYardAvailable();
                double piece = item.getPieceAvailable() - itemDetails.getPieceAvailable();
                item.setYardAvailable(yard);
                item.setPieceAvailable(piece);
                item.setPiecesOnHold(itemDetails.getPieceAvailable());
                item.setYardsOnHold(itemDetails.getYardAvailable());
                log.info("inside Hold - {}",item);
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

    public Object[] getItemsByColor(Long value) {
        log.info("Fetching items by color: {}", value);

        Object[] colors =  inventoryRepository.findDistinctColorByProduct_Id(value)
                .stream()
                .map(Inventory::getColor)
                .distinct().toArray();

        log.info("Distinct colors found: {}", colors);
        return colors;
    }

    public Inventory updateInventoryOnSale(Long productId, double saleYards, double salePieces, String color, String category) {
        Inventory inventory = inventoryRepository.findByProduct_IdAndColorAndCategory(productId, color, category);
        if (inventory == null) {
            throw new RuntimeException("Inventory not found for productId: " + productId + ", color: " + color + ", category: " + category);
        }
        inventory.setYardAvailable(inventory.getYardAvailable() - saleYards);
        inventory.setPieceAvailable(inventory.getPieceAvailable() - salePieces);
        inventory.setSaleYards(inventory.getSaleYards() + saleYards);
        inventory.setSalePieces(inventory.getSalePieces() + salePieces);
        return inventoryRepository.save(inventory);
    }

    public Object[] getItemsByProductAndColor(Long value, String color) {
        log.info("Fetching items by product ID: {} and color: {}", value, color);
        List<Inventory> inventories = inventoryRepository.findDistinctCategoryByProduct_IdAndColor(value, color);

        return inventories.stream()
                .map(Inventory::getCategory)
                .distinct()
                .toArray();
    }
}