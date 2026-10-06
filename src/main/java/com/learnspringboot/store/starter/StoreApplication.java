// package com.learnspringboot.store.starter;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.ApplicationContext;

// @SpringBootApplication
// public class StoreApplication {

// public void run(String[] args) {
// ApplicationContext context = SpringApplication.run(StoreApplication.class,
// args);
// var iocOrderService = context.getBean(OrderService.class);
// iocOrderService.placeOrder();

// // không xảy ra DI
// var orderService = new OrderService();

// // nên phải tự tạo object và tự set
// orderService.setPaymentService(new StripePaymentService());
// orderService.placeOrder();

// orderService.setPaymentService(new PaypalPaymentService());
// orderService.placeOrder();

// }

// }
