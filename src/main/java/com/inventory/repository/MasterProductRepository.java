package com.inventory.repository;

import com.inventory.model.MasterProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterProductRepository extends JpaRepository<MasterProduct, Long> {
    Optional<MasterProduct> findByProductName(String productName);

}