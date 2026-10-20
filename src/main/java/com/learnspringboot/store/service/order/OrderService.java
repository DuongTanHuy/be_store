package com.learnspringboot.store.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.learnspringboot.store.dto.OrderDto;
import com.learnspringboot.store.enums.OrderStatus;
import com.learnspringboot.store.exceptions.ResourceNotFoundException;
import com.learnspringboot.store.model.Cart;
import com.learnspringboot.store.model.Order;
import com.learnspringboot.store.model.OrderItem;
import com.learnspringboot.store.model.Product;
import com.learnspringboot.store.model.User;
import com.learnspringboot.store.repository.CartRepository;
import com.learnspringboot.store.repository.OrderRepository;
import com.learnspringboot.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    private OrderDto mapToOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    private List<OrderDto> mapToListOrderDto(List<Order> orders) {
        return orders.stream().map(this::mapToOrderDto).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            int newInventory = product.getInventory() - cartItem.getQuantity();
            if (newInventory < 0) {
                throw new IllegalStateException("Inventory became negative for product: " + product.getId());
            }
            product.setInventory(newInventory);
            productRepository.save(product);

            return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
        }).toList();
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;
    }

    @Override
    public OrderDto placeOrder(Long userId) {
        Cart cart = Optional.ofNullable(cartRepository.findByUserId(userId)).map(c -> c)
                .orElseThrow(() -> new ResourceNotFoundException("User's cart is empty!"));

        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));

        Order newOrder = orderRepository.save(order);

        User user = cart.getUser();
        if (user != null) {
            user.setCart(null);
        }
        cart.setUser(null);
        cartRepository.delete(cart);

        OrderDto orderDto = mapToOrderDto(newOrder);

        return orderDto;
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));

        return mapToOrderDto(order);
    }

    @Override
    public List<OrderDto> getUserOrder(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return mapToListOrderDto(orders);
    }

}
