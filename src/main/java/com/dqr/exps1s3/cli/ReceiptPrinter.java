package com.dqr.exps1s3.cli;

import com.dqr.exps1s3.model.Order;
import com.dqr.exps1s3.view.CartView;

/** Imprime un resumen de compra. */
public final class ReceiptPrinter {
    private final CartView cartView = new CartView();

    public void print(Order order) {
        System.out.println("\n===== RESUMEN DE COMPRA =====");
        cartView.render(order);
        System.out.println("Gracias por su compra!\n");
    }
}
