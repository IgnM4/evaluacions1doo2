package com.dqr.exps1s3;

import com.dqr.exps1s3.controller.CartController;
import com.dqr.exps1s3.controller.DiscountController;
import com.dqr.exps1s3.controller.ProductController;
import com.dqr.exps1s3.model.Order;
import com.dqr.exps1s3.model.User;
import com.dqr.exps1s3.view.CartView;
import com.dqr.exps1s3.view.DiscountView;
import com.dqr.exps1s3.view.ProductListView;

public class App {
    public static void main(String[] args) {
        // Usuario y pedido
        User u = new User("U001", "Ignacio Astorga", "ignacio@example.com");
        Order order = new Order("O-1001", u);

        // Controladores
        ProductController productController = new ProductController();
        productController.seedDemoProducts();

        CartController cartController = new CartController(order);

        DiscountController discountController = new DiscountController();
        discountController.registerSimpleCodes();

        // Vistas
        ProductListView productListView = new ProductListView();
        CartView cartView = new CartView();
        DiscountView discountView = new DiscountView();

        // Paso 1: catálogo
        productListView.render(productController.list());

        // Paso 2: carrito
        cartController.addProduct(productController.findBySku("P01"), 2);
        cartController.addProduct(productController.findBySku("P02"), 1);
        cartController.addProduct(productController.findBySku("P03"), 1);
        cartView.render(order);

        // Paso 3: pipelines
        discountView.renderPipeline("SALE10", discountController.getPipeline("SALE10"));
        discountView.renderPipeline("TOP20",  discountController.getPipeline("TOP20"));
        discountView.renderPipeline("COMBO10",discountController.getPipeline("COMBO10"));

        // Paso 4: aplicar códigos
        cartController.addDiscountToItem("P01", "TOP20");  // -20% a poleras (TOP)
        cartController.addDiscountToItem("P03", "SALE10"); // -10% a chaqueta
        cartController.addOrderLevelDiscount("SALE10");    // -10% al total del pedido
        cartView.render(order);

        // Paso 5: quitar jeans y recalcular
        cartController.removeProduct("P02");
        cartView.render(order);
    }
}
