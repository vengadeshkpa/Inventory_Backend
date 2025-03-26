package com.inventory.controller;

import com.inventory.model.Inventory;
import com.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService service;

    @GetMapping
    public List<Inventory> getAllItems() {
        return service.getAllItems();
    }

    @GetMapping("/{id}")
    public Inventory getItemById(@PathVariable Long id) {
        return service.getItemById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found"));
    }

    @PostMapping
    public Inventory addItem(@RequestBody Map<String, Object> request) {
        String productName = (String) request.get("productName");
        Inventory item = new Inventory();
        item.setColor((String) request.get("color"));
        item.setCategory((String) request.get("category"));
        item.setYardAvailable(Double.parseDouble(request.get("yardAvailable").toString()));
        item.setPieceAvailable(Double.parseDouble(request.get("pieceAvailable").toString()));

        return service.addItem(productName, item);
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
        itemDetails.setColor((String) request.get("color"));
        itemDetails.setCategory((String) request.get("category"));
        itemDetails.setYardAvailable(Double.parseDouble(request.get("yardAvailable").toString()));
        itemDetails.setPieceAvailable(Double.parseDouble(request.get("pieceAvailable").toString()));

        return service.updateItemCond(id, productName, itemDetails,cond);
    }


    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
        return "Item deleted successfully!";
    }
}