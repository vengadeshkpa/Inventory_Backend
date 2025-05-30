package com.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column(nullable = false)
    private String category;
}

