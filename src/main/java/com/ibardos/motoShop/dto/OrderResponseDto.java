package com.ibardos.motoShop.dto;

import com.ibardos.motoShop.entity.Order;
import com.ibardos.motoShop.util.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO class for Order entity to be sent to the frontend.
 * <p>
 * This class holds only the fields that are needed for the frontend.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {
    private int id;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private LocalDate estimatedDeliveryDate;
    private int motorcycleStockId;
    private String motorcycleModelName;
    private int customerId;
    private String customerFullName;


    /**
     * Constructor that maps Order entity to OrderResponseDto.
     */
    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getOrderStatus();
        this.originalPrice = order.getOriginalPrice();
        this.discount = order.getDiscount();
        this.totalAmount = order.getTotalAmount();
        this.estimatedDeliveryDate = order.getEstimatedDeliveryDate();
        this.motorcycleStockId = order.getMotorcycleStock().getId();
        this.motorcycleModelName = order.getMotorcycleStock().getMotorcycleModel().getManufacturer().getName() + " - " + order.getMotorcycleStock().getMotorcycleModel().getModelName();
        this.customerId = order.getCustomer().getId();
        this.customerFullName = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName();
    }
}