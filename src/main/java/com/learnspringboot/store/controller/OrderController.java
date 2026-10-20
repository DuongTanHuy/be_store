package com.learnspringboot.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learnspringboot.store.dto.OrderDto;
import com.learnspringboot.store.response.ApiResponse;
import com.learnspringboot.store.service.order.IOrderService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}")
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id) {
        OrderDto orderDto = orderService.getOrder(id);

        return ResponseEntity.ok(new ApiResponse("Get order successfully!", orderDto));
    }

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        OrderDto orderDto = orderService.placeOrder(userId);

        return ResponseEntity.ok(new ApiResponse("Create order successfully!", orderDto));
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrder(@PathVariable Long userId) {
        List<OrderDto> orderDtos = orderService.getUserOrder(userId);

        return ResponseEntity.ok(new ApiResponse("Get user's order successfully!", orderDtos));
    }

}
