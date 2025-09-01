package com.dqr.exps1s3.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Product {
    private final String sku;
    private final String name;
    private final String category;
    private final BigDecimal unitPrice;

    public Product(String sku, String name, String category, BigDecimal unitPrice) {
        this.sku = Objects.requireNonNull(sku);
        this.name = Objects.requireNonNull(name);
        this.category = Objects.requireNonNull(category);
        this.unitPrice = Objects.requireNonNull(unitPrice);
    }

    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public BigDecimal getUnitPrice() { return unitPrice; }
}
