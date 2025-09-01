package com.dqr.exps1s3.controller;

import com.dqr.exps1s3.model.Order;
import com.dqr.exps1s3.model.Product;

public final class CartController {
    private final Order order;
    public CartController(Order order) { this.order = order; }

    public void addProduct(Product p, int qty) { order.addItem(p, qty); }
    public void removeProduct(String sku) { order.removeItem(sku); }

    public void addDiscountToItem(String sku, String code) {
        Order.LineItem li = order.getItems().stream()
                .filter(x -> x.getProduct().getSku().equalsIgnoreCase(sku))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ítem no encontrado: " + sku));
        li.getDiscountCodes().add(code.toUpperCase());
    }

    public void addOrderLevelDiscount(String code) {
        order.getOrderLevelDiscountCodes().add(code.toUpperCase());
    }
}
