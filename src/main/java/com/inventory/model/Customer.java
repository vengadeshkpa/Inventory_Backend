package com.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(nullable = false)
    private String name;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "internal_salesman_id")
    private InternalSalesMan internalSalesMan;

    @Column(name = "location")
    private String location;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "contact_number1")
    private String contactNumber1;

    @Column(name = "contact_number2")
    private String contactNumber2;

    @Column(name = "customer_poc")
    private String customerPOC;

    @Column(name = "customer_poc_contact")
    private String customerPOCContact;
}