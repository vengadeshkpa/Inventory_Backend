package com.inventory.controller;

import com.inventory.model.Inventory;
import com.inventory.model.MasterProduct;
import com.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);
    @Autowired
    private InventoryService service;

    @GetMapping
    public List<Inventory> getAllItems() {
        return service.getAllItems();
    }

    @GetMapping ("master-products")
    public List<MasterProduct> getMasterProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Inventory getItemById(@PathVariable Long id) {
        return service.getItemById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found"));
    }

    @PostMapping
    public Inventory addItem(@RequestBody Map<String, Object> request) {
        String productName = (String) request.get("productName");
        String color = (String) request.get("color");
        String category = (String) request.get("category");
        double yardAvailable = Double.parseDouble(request.get("yardAvailable").toString());
        double pieceAvailable = Double.parseDouble(request.get("pieceAvailable").toString());
        String inventoryUnit = (String) request.get("inventoryUnit");

        Long masterProductId = service.getMasterProductId(productName);

        Inventory existing = service.getItemByProductColorCategory(masterProductId, color, category);

        if (existing != null) {
            existing.setYardAvailable(existing.getYardAvailable() + yardAvailable);
            existing.setPieceAvailable(existing.getPieceAvailable() + pieceAvailable);
            existing.setLoadedYards(existing.getLoadedYards() + yardAvailable);
            existing.setLoadedPieces(existing.getLoadedPieces() + pieceAvailable);
            existing.setInventoryUnit(inventoryUnit);
            return service.updateItem(existing.getId(), productName, existing);
        } else {
            Inventory item = new Inventory();
            item.setColor(color);
            item.setCategory(category);
            item.setYardAvailable(yardAvailable);
            item.setPieceAvailable(pieceAvailable);
            item.setLoadedYards(yardAvailable);
            item.setLoadedPieces(pieceAvailable);
            item.setInventoryUnit(inventoryUnit);
            return service.addItem(productName, item);
        }


    }

    @PostMapping("/addProduct")
    public MasterProduct addProduct(@RequestBody Map<String, Object> request) {
        MasterProduct item = new MasterProduct();
        item.setProductName((String) request.get("addProductName"));
        return service.addProduct(item);
    }


    @PutMapping("/{id}")
    public Inventory updateItem(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        String productName = (String) request.get("productName");

        Inventory itemDetails = new Inventory();
        itemDetails.setColor((String) request.get("color"));
        itemDetails.setCategory((String) request.get("category"));
        itemDetails.setYardAvailable(Double.parseDouble(request.get("yardAvailable").toString()));
        itemDetails.setPieceAvailable(Double.parseDouble(request.get("pieceAvailable").toString()));


        return service.updateItem(id, productName, itemDetails);
    }

    @PutMapping("/updatecond/{id}/{cond}")
    public Inventory updateItemWithCond(@PathVariable Long id,@PathVariable String cond, @RequestBody Map<String, Object> request) {
        String productName = (String) request.get("productName");

        Inventory itemDetails = new Inventory();
        itemDetails.setId(id);
        itemDetails.setColor((String) request.get("color"));
        itemDetails.setCategory((String) request.get("category"));
        itemDetails.setYardAvailable(Double.parseDouble(request.get("yardAvailable").toString()));
        itemDetails.setPieceAvailable(Double.parseDouble(request.get("pieceAvailable").toString()));

        log.info("Item Details: {}", itemDetails);

        return service.updateItemCond(id, productName, itemDetails,cond);
    }


    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
        return "Item deleted successfully!";
    }

    @GetMapping("/colors/{value}")
    public Object[] getItemsByColor(@PathVariable Long value) {
        log.info("Fetching colors by Master Product: {}", value);
        return service.getItemsByColor(value);
    }

    @GetMapping("/categories/{value}/{color}")
    public Object[] getItemsByProductColor(@PathVariable Long value, @PathVariable String color) {
        log.info("Fetching Categories by Master Product & Color: {}", value);
        return service.getItemsByProductAndColor(value,color);
    }

    @PutMapping("/sale")
    public Inventory updateInventoryOnSale(@RequestBody Map<String, Object> request) {
        Long productId = Long.parseLong(request.get("productId").toString());
        double saleYards = Double.parseDouble(request.get("saleYards").toString());
        double salePieces = Double.parseDouble(request.get("salePieces").toString());
        String color = (String) request.get("color");
        String category = (String) request.get("category");
        return service.updateInventoryOnSale(productId, saleYards, salePieces, color, category);
    }

}