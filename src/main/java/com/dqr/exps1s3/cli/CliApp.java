package com.dqr.exps1s3.cli;

import com.dqr.exps1s3.controller.CartController;
import com.dqr.exps1s3.controller.DiscountController;
import com.dqr.exps1s3.controller.ProductController;
import com.dqr.exps1s3.model.Order;
import com.dqr.exps1s3.model.User;

/** Punto de entrada para la aplicación CLI. */
public final class CliApp {
    public static void main(String[] args) {
        ProductController pc = new ProductController();
        pc.seedDemoProducts();

        DiscountController dc = new DiscountController();
        dc.registerSimpleCodes();

        User u = new User("U1", "Cliente CLI", "cli@example.com");
        Order order = new Order("O1", u);
        CartController cc = new CartController(order);

        ConsoleMenu menu = new ConsoleMenu(pc, cc, dc, order);
        menu.start();
    }
}
