package com.ibardos.motoShop.dto;

import com.ibardos.motoShop.util.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO class for Order entity to be sent to the backend.
 * <p>
 * This class holds only the fields that are needed for the backend to create a new Order entity.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {
    private int id;
    private OrderStatus orderStatus;
    private BigDecimal discount;
    private LocalDate estimatedDeliveryDate;
    private int motorcycleStockId;
    private int customerId;
}