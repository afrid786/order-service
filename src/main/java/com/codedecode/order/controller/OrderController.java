package com.codedecode.order.controller;


import com.codedecode.order.dto.OrderDTO;
import com.codedecode.order.dto.OrderDTOFromFE;
import com.codedecode.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/createOrder")
    public ResponseEntity<OrderDTO> createOrder (@RequestBody OrderDTOFromFE orderDetails){
        OrderDTO orderSavedInDB = orderService.createOrderInMongo(orderDetails);
        return new ResponseEntity<>(orderSavedInDB, HttpStatus.CREATED);
    }



}
