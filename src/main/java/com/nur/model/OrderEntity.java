package com.nur.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", unique = true, nullable = false)
    private String orderId;

    private String customerName;

    private String productName;

    private String status;

    private String currentLocation;

    private LocalDate expectedDelivery;

    private BigDecimal totalAmount;

    private String paymentStatus;

}