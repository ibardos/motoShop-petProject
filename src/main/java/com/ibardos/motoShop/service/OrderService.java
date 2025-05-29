package com.ibardos.motoShop.service;

import com.ibardos.motoShop.dto.OrderRequestDto;
import com.ibardos.motoShop.dto.OrderResponseDto;
import com.ibardos.motoShop.entity.Customer;
import com.ibardos.motoShop.entity.MotorcycleStock;
import com.ibardos.motoShop.exception.InsufficientStockException;
import com.ibardos.motoShop.repository.CustomerRepository;
import com.ibardos.motoShop.repository.MotorcycleStockRepository;
import com.ibardos.motoShop.repository.OrderRepository;
import com.ibardos.motoShop.entity.Order;
import com.ibardos.motoShop.util.OrderStatus;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class that handles business logic for Order entities.
 * <p>
 * Provides methods for CRUD operations, order-related stock manipulation,
 * and data transformation between Order entities and DTOs.
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MotorcycleStockRepository motorcycleStockRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, MotorcycleStockRepository motorcycleStockRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.motorcycleStockRepository = motorcycleStockRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Creates a new Order in the database with the details provided in the DTO.
     * Validates stock availability and decreases stock level for the ordered motorcycle.
     * Maps relationships between Order, MotorcycleStock and Customer entities.
     *
     * @param orderRequestDto Order details from client 
     * @return OrderResponseDto containing the saved order details
     * @throws InsufficientStockException if stock level is 0
     * @throws EntityNotFoundException if related entities not found
     */
    public OrderResponseDto add(OrderRequestDto orderRequestDto) {
        MotorcycleStock motorcycleStock = getMotorcycleStockById(orderRequestDto.getMotorcycleStockId());

        if (isStockInsufficient(motorcycleStock)) {
            throw new InsufficientStockException("Insufficient stock for the selected motorcycle. MotorcycleStock ID: " + motorcycleStock.getId());
        }

        Customer customer = getCustomerById(orderRequestDto.getCustomerId());

        Order order = createOrderFromOrderRequestDto(orderRequestDto, motorcycleStock, customer);
        Order savedOrder = orderRepository.save(order);

        decreaseAmountOfStock(motorcycleStock);

        return new OrderResponseDto(savedOrder);
    }

    /**
     * Retrieves a single Order from the database by its ID.
     * Maps the Order entity to a DTO for client response.
     *
     * @param id unique identifier of the Order
     * @return OrderResponseDto containing order details
     * @throws EntityNotFoundException if order not found
     */
    public OrderResponseDto get(int id) {
        Order orderFromDb = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + id + " not found."));

        return new OrderResponseDto(orderFromDb);
    }

    /**
     * Retrieves all Orders from the database, ordered by date descending, related to a specific Customer.
     *
     * @param customerId Order identifier passed as path variable
     * @return OrderResponseDto containing the order details
     */
    public List<OrderResponseDto> getByCustomerId(int customerId) {
        // Check if Customer exists
        customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + customerId + " not found."));

        List<Order> orders = orderRepository.findAllByCustomerIdOrderByOrderDateDesc(customerId);

        return orders.stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    /**
     * Retrieves all Orders from the database ordered by order date descending.
     * Maps Order entities to DTOs for client response.
     *
     * @return List of OrderResponseDto containing all orders
     * @throws EntityNotFoundException if no orders exist
     */
    public List<OrderResponseDto> getAll() {
        List<Order> orders = orderRepository.findAllByOrderByOrderDateDesc();

        if (orders.isEmpty()) {
            throw new EntityNotFoundException("No orders found.");
        }

        return orders.stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    /**
     * Updates an existing Order in the database.
     * Handles motorcycle stock changes if order is modified to a different motorcycle.
     * Updates stock levels and entity relationships appropriately.
     * Preserves original order date while updating other fields.
     *
     * @param orderRequestDto updated Order details from client
     * @throws InsufficientStockException if new motorcycle stock level is 0
     * @throws EntityNotFoundException    if order, motorcycle stock or customer not found
     */
    public void update(OrderRequestDto orderRequestDto) {
        // Existing data fetched from the database
        Order orderFromDb = getOrderById(orderRequestDto.getId());
        MotorcycleStock motorcycleStockFromDb = orderFromDb.getMotorcycleStock();

        // Data coming from the client
        MotorcycleStock motorcycleStockTarget = getMotorcycleStockById(orderRequestDto.getMotorcycleStockId());
        Customer customerTarget = getCustomerById(orderRequestDto.getCustomerId());

        if (isMotorcycleStockChanged(orderFromDb, orderRequestDto)) {
            if (isStockInsufficient(motorcycleStockTarget)) {
                throw new InsufficientStockException("Insufficient stock for the selected motorcycle. MotorcycleStock ID: " + motorcycleStockTarget.getId());
            }

            increaseAmountOfStock(motorcycleStockFromDb);
            decreaseAmountOfStock(motorcycleStockTarget);
        }

        Order updatedOrder = createOrderFromOrderRequestDto(orderRequestDto, motorcycleStockTarget, customerTarget, orderFromDb.getOrderDate());
        orderRepository.save(updatedOrder);
    }

    /**
     * Deletes an Order from the database by its ID.
     * Increases the stock level of the associated motorcycle.
     *
     * @param id unique identifier of Order to delete
     * @throws EntityNotFoundException if order not found
     */
    public void delete(int id) {
        Order orderFromDb = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + id + " not found."));

        increaseAmountOfStock(orderFromDb.getMotorcycleStock());

        orderRepository.deleteById(id);
    }


    // Private helper methods
    /**
     * Creates a new Order entity from DTO and related entities.
     * Calculates total amount based on original price and discount.
     *
     * @param dto             The order request data transfer object
     * @param motorcycleStock The motorcycle stock entity
     * @param customer        The customer entity
     * @param orderDate       The date when order was placed
     * @return New Order entity with calculated total amount
     */
    private Order createOrderFromOrderRequestDto(OrderRequestDto dto, MotorcycleStock motorcycleStock, Customer customer, LocalDate orderDate) {
        int id = dto.getId();
        OrderStatus orderStatus = dto.getOrderStatus();
        BigDecimal originalPrice = motorcycleStock.getSellingPrice();
        BigDecimal discount = dto.getDiscount();
        BigDecimal totalAmount = originalPrice.subtract(originalPrice.multiply(discount));
        LocalDate estimatedDeliveryDate = dto.getEstimatedDeliveryDate();

        return new Order(id, orderDate, orderStatus, originalPrice, discount, totalAmount, estimatedDeliveryDate, motorcycleStock, customer);
    }

    /**
     * Overloaded version that creates Order entity with current date, suitable for newly created Orders.
     *
     * @param dto             The order request data transfer object
     * @param motorcycleStock The motorcycle stock entity
     * @param customer        The customer entity
     * @return New Order entity with current date and calculated total amount
     */
    private Order createOrderFromOrderRequestDto(OrderRequestDto dto, MotorcycleStock motorcycleStock, Customer customer) {
        return createOrderFromOrderRequestDto(dto, motorcycleStock, customer, LocalDate.now());
    }

    /**
     * Retrieves Order from database by ID or throws EntityNotFoundException.
     */
    private Order getOrderById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: " + id + " not found."));
    }

    /**
     * Retrieves MotorcycleStock from database by ID or throws EntityNotFoundException.
     */
    private MotorcycleStock getMotorcycleStockById(int id) {
        return motorcycleStockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MotorcycleStock with id: " + id + " not found."));
    }

    /**
     * Retrieves Customer from database by ID or throws EntityNotFoundException.
     */
    private Customer getCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: " + id + " not found."));
    }

    /**
     * Checks if motorcycle stock has changed between existing order and update request.
     */
    private boolean isMotorcycleStockChanged(Order orderFromDb, OrderRequestDto orderRequestDto) {
        return orderFromDb.getId() != orderRequestDto.getMotorcycleStockId();
    }

    /**
     * Checks if motorcycle stock level is zero or negative.
     */
    private boolean isStockInsufficient(MotorcycleStock motorcycleStock) {
        return motorcycleStock.getInStock() <= 0;
    }

    /**
     * Reduces stock level by 1 and updates repository.
     */
    private void decreaseAmountOfStock(MotorcycleStock motorcycleStock) {
        motorcycleStock.setInStock(motorcycleStock.getInStock() - 1);
        motorcycleStockRepository.save(motorcycleStock);
    }

    /**
     * Increases stock level by 1 and updates repository.
     */
    private void increaseAmountOfStock(MotorcycleStock motorcycleStock) {
        motorcycleStock.setInStock(motorcycleStock.getInStock() + 1);
        motorcycleStockRepository.save(motorcycleStock);
    }
}