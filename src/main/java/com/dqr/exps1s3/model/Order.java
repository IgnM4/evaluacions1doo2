package com.dqr.exps1s3.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public final class Order {
    public static final class LineItem {
        private final Product product;
        private int quantity;
        private final List<String> discountCodes = new ArrayList<>();

        public LineItem(Product product, int quantity) {
            if (quantity <= 0) throw new IllegalArgumentException("quantity > 0");
            this.product = Objects.requireNonNull(product);
            this.quantity = quantity;
        }
        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int q) { if (q <= 0) throw new IllegalArgumentException("q > 0"); this.quantity = q; }
        public List<String> getDiscountCodes() { return discountCodes; }

        public BigDecimal subtotal() {
            return product.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
        }
    }

    private final String id;
    private final User customer;
    private final LocalDateTime createdAt;
    private final Map<String, LineItem> itemsBySku = new HashMap<>();
    private final List<String> orderLevelDiscountCodes = new ArrayList<>();

    public Order(String id, User customer) {
        this.id = Objects.requireNonNull(id);
        this.customer = Objects.requireNonNull(customer);
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public User getCustomer() { return customer; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public List<LineItem> getItems() {
        return itemsBySku.values().stream()
                .sorted(Comparator.comparing(i -> i.getProduct().getSku()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void addItem(Product p, int qty) {
        LineItem li = itemsBySku.get(p.getSku());
        if (li == null) itemsBySku.put(p.getSku(), new LineItem(p, qty));
        else li.setQuantity(li.getQuantity() + qty);
    }

    public void removeItem(String sku) { itemsBySku.remove(sku); }

    public BigDecimal subtotal() {
        return getItems().stream().map(LineItem::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<String> getOrderLevelDiscountCodes() { return orderLevelDiscountCodes; }
}
