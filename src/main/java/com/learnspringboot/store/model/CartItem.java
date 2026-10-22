package com.learnspringboot.store.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
    }
}

// CartItem tồn tại phụ thuộc vào Cart, không độc lập, nó phải có quan hệ 2
// chiều với Cart để:
// Từ Cart → cần lấy danh sách CartItem (để hiển thị giỏ hàng, tính tổng tiền…)
// Từ CartItem → cần biết nó thuộc Cart nào (để cascade, orphanRemoval, đồng
// bộ…)

// Thêm, cập nhât, xóa cartItem phải luôn thông qua Cart
// CartItem là Owning side (chứa foreign key)
// Cart là Inverse side (thường có cascade và orphanRemoval=true) để khi thao
// tác Cart thì CartItem cũng được thao tác theo