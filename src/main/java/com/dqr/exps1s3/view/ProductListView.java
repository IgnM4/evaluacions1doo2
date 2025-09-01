package com.dqr.exps1s3.view;

import com.dqr.exps1s3.model.Product;
import java.util.List;
import static com.dqr.exps1s3.util.Money.*;

public final class ProductListView {
    public void render(List<Product> products) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println(  "║                CATÁLOGO DE PRODUCTOS             ║");
        System.out.println(  "╠════╦══════════════════════════╦════════╦═════════╣");
        System.out.println(  "║ SKU║ Nombre                   ║ Cat.   ║ Precio  ║");
        System.out.println(  "╠════╬══════════════════════════╬════════╬═════════╣");
        for (Product p : products) {
            System.out.printf("║%-4s║ %-24s ║ %-6s ║ $%7s ║%n",
                    p.getSku(), trunc(p.getName(),24), trunc(p.getCategory(),6), dinero(p.getUnitPrice()));
        }
        System.out.println(  "╚════╩══════════════════════════╩════════╩═════════╝");
    }
}
