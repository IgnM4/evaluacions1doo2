package com.dqr.exps1s3.controller;

import com.dqr.exps1s3.model.Product;
import java.math.BigDecimal;
import java.util.*;

public final class ProductController {
    private final List<Product> catalog = new ArrayList<>();

    public void seedDemoProducts() {
        catalog.add(new Product("P01","Polera Básica", "TOP", new BigDecimal("14990")));
        catalog.add(new Product("P02","Jeans Regular", "BOTTOM", new BigDecimal("29990")));
        catalog.add(new Product("P03","Chaqueta Urban", "TOP", new BigDecimal("49990")));
        catalog.add(new Product("P04","Zapatillas Run", "SHOES", new BigDecimal("55990")));
    }

    public List<Product> list() {
        return Collections.unmodifiableList(catalog);
    }

    public Product findBySku(String sku) {
        return catalog.stream()
                .filter(p -> p.getSku().equalsIgnoreCase(sku))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: "+sku));
    }
}
