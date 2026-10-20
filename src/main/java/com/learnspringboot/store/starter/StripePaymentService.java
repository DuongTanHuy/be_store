package com.learnspringboot.store.starter;

import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentServiceStarter {
    /**
     * @see com.learnspringboot.store.starter.PaymentServiceStarter#processPayment(double)
     */
    @Override
    public void processPayment(double amount) {
        System.out.println("STRIPE");
        System.out.println("Amount: " + amount);
    }
}
