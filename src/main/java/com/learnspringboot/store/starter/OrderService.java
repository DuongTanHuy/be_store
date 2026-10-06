package com.learnspringboot.store.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Service
@Component
public class OrderService {
    private PaymentService paymentService;

    // nếu chỉ có 1 contructor thì không cần @Autowired
    public OrderService() {
    }

    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        paymentService.processPayment(10);
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
