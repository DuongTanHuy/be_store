package com.learnspringboot.store.starter;

// Stripe ưu tiên vì Paypal không phải là một Spring bean 
// Nếu Paypal cũng là một Spring bean thì có thể dùng (@Qualifier("paypalPaymentService"), @Primary) để chỉ định cho Spring IoC Container biết sử dụng bean nào.
public class PaypalPaymentService implements PaymentService {
    /**
     * @see com.learnspringboot.store.starter.PaymentService#processPayment(double)
     */
    @Override
    public void processPayment(double amount) {
        System.out.println("PAYPAL");
        System.out.println("Amount: " + amount);
    }
}
