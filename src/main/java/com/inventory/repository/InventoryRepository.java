package com.inventory.repository;

import com.inventory.model.Inventory;
import com.inventory.model.MasterProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findDistinctColorByProduct_Id(Long productId);

    List<Inventory> findDistinctCategoryByProduct_IdAndColor(Long productId, String color);

    Inventory findByProduct_IdAndColorAndCategory(Long productId, String color, String category);
}
