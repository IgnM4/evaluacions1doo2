package com.dqr.exps1s3.view;

import com.dqr.exps1s3.model.Order;
import com.dqr.exps1s3.pricing.DiscountManager;
import java.math.BigDecimal;
import static com.dqr.exps1s3.util.Money.*;

public final class CartView {
    private final DiscountManager dm = DiscountManager.getInstance();

    public void render(Order order) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println(  "║                  CARRITO DE COMPRAS              ║");
        System.out.println(  "╠════╦══════════════════════════╦══════╦═══════════╣");
        System.out.println(  "║ SKU║ Item                     ║ Cant ║  Total    ║");
        System.out.println(  "╠════╬══════════════════════════╬══════╬═══════════╣");
        for (Order.LineItem li : order.getItems()) {
            BigDecimal total = dm.totalForLineItem(li);
            System.out.printf("║%-4s║ %-24s ║ %4d ║ $%8s ║%n",
                    li.getProduct().getSku(),
                    trunc(li.getProduct().getName(),24),
                    li.getQuantity(),
                    dinero(total));
            if (!li.getDiscountCodes().isEmpty()) {
                System.out.printf("║    ║   Códigos: %-33s║%n", String.join(", ", li.getDiscountCodes()));
            }
        }
        System.out.println(  "╠════╩══════════════════════════╩══════╩═══════════╣");
        System.out.printf (  "║ Subtotal: $%9s                               ║%n", dinero(order.subtotal()));
        if (!order.getOrderLevelDiscountCodes().isEmpty()) {
            System.out.printf("║ Códigos de pedido: %s%n", String.join(", ", order.getOrderLevelDiscountCodes()));
        }
        System.out.printf (  "║   TOTAL : $%9s                               ║%n", dinero(dm.totalForOrder(order)));
        System.out.println(  "╚══════════════════════════════════════════════════╝");
    }
}
