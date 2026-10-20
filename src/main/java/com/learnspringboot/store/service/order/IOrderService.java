package com.learnspringboot.store.service.order;

import java.util.List;

import com.learnspringboot.store.dto.OrderDto;

public interface IOrderService {
    OrderDto placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrder(Long userId);
}
