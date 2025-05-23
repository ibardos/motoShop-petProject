package com.ibardos.motoShop.entity;

import com.ibardos.motoShop.util.OrderStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity class representing an Order in the database.
 * <p>
 * This class maintains the relationship between a customer and their order details.
 * Each order is uniquely identified and associated with exactly one customer through a many-to-one relationship.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private LocalDate estimatedDeliveryDate;
    @OneToOne
    private MotorcycleStock motorcycleStock;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}