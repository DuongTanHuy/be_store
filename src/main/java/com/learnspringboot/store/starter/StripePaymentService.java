package com.learnspringboot.store.starter;

import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService {
    /**
     * @see com.learnspringboot.store.starter.PaymentService#processPayment(double)
     */
    @Override
    public void processPayment(double amount) {
        System.out.println("STRIPE");
        System.out.println("Amount: " + amount);
    }
}
