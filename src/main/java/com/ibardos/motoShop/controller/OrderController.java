package com.ibardos.motoShop.controller;

import com.ibardos.motoShop.dto.OrderRequestDto;
import com.ibardos.motoShop.dto.OrderResponseDto;
import com.ibardos.motoShop.exception.InsufficientStockException;
import com.ibardos.motoShop.service.OrderService;

import jakarta.persistence.EntityNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST API controller handling Order-related HTTP requests.
 * <p>
 * Provides endpoints for CRUD operations on Orders including creation, retrieval,
 * update and deletion of orders. Handles stock management and validation as part
 * of order processing.
 */
@RestController
@RequestMapping("service/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Creates a new Order in the database.
     *
     * @param orderRequestDto Order details provided in request body
     * @return OrderResponseDto containing the created order details
     * @throws ResponseStatusException with:
     *         HttpStatus.BAD_REQUEST if request data is invalid
     *         HttpStatus.NOT_FOUND if referenced entities don't exist
     *         HttpStatus.CONFLICT if insufficient stock available
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add")
    public OrderResponseDto add(@RequestBody OrderRequestDto orderRequestDto) {
        try {
            return orderService.add(orderRequestDto);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InsufficientStockException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /**
     * Retrieves a specific Order by its ID.
     *
     * @param id Order identifier passed as path variable
     * @return OrderResponseDto containing the order details
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if order doesn't exist
     */
    @GetMapping("get/{id}")
    public OrderResponseDto get(@PathVariable int id) {
        try {
            return orderService.get(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Retrieves all Orders related to a specific Customer.
     *
     * @param customerId the Customer's unique identifier whom Orders should be retrieved
     * @return List of OrderResponseDto objects related to a specific Customer
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if Customer doesn't exist
     */
    @GetMapping("get/byCustomerId/{customerId}")
    public List<OrderResponseDto> getByCustomerId(@PathVariable int customerId) {
        try {
            return orderService.getByCustomerId(customerId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * Updates an existing Order in the database.
     *
     * @param orderRequestDto Updated order details in request body
     * @throws ResponseStatusException with:
     *         HttpStatus.BAD_REQUEST if request data is invalid
     *         HttpStatus.NOT_FOUND if order or referenced entities don't exist
     *         HttpStatus.CONFLICT if insufficient stock available
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("update")
    public void update(@RequestBody OrderRequestDto orderRequestDto) {
        try {
            orderService.update(orderRequestDto);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InsufficientStockException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    /**
     * Deletes an Order from the database by its ID.
     *
     * @param id Order identifier passed as path variable
     * @throws ResponseStatusException with HttpStatus.NOT_FOUND if order doesn't exist
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable int id) {
        try {
            orderService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
