package com.dqr.exps1s3.cli;

import com.dqr.exps1s3.controller.CartController;
import com.dqr.exps1s3.controller.DiscountController;
import com.dqr.exps1s3.controller.ProductController;
import com.dqr.exps1s3.model.Order;
import com.dqr.exps1s3.model.Product;
import com.dqr.exps1s3.pricing.DiscountManager;
import com.dqr.exps1s3.view.CartView;
import com.dqr.exps1s3.view.DiscountView;
import com.dqr.exps1s3.view.ProductListView;

import java.util.ArrayList;
import java.util.List;

/** Bucle principal del menú de consola. */
public final class ConsoleMenu {
    private final ProductController productController;
    private final CartController cartController;
    private final DiscountController discountController;
    private final Order order;
    private final DiscountManager dm = DiscountManager.getInstance();
    private final ProductListView productListView = new ProductListView();
    private final CartView cartView = new CartView();
    private final DiscountView discountView = new DiscountView();
    private final ReceiptPrinter receiptPrinter = new ReceiptPrinter();
    private final Input input = new Input();

    public ConsoleMenu(ProductController pc, CartController cc, DiscountController dc, Order order) {
        this.productController = pc;
        this.cartController = cc;
        this.discountController = dc;
        this.order = order;
    }

    public void start() {
        boolean running = true;
        while (running) {
            mostrarMenu();
            int opt = input.leerIntSeguro("Opción");
            switch (opt) {
                case 1 -> verCatalogo();
                case 2 -> agregarProducto();
                case 3 -> quitarProducto();
                case 4 -> aplicarDescuentoItem();
                case 5 -> aplicarDescuentoPedido();
                case 6 -> verCarrito();
                case 7 -> finalizarCompra();
                case 8 -> mostrarAyuda();
                case 9 -> running = false;
                default -> System.out.println("Opción inválida.");
            }
        }
        System.out.println("Hasta pronto!");
    }

    private void mostrarMenu() {
        System.out.println("\n==== MENÚ ====");
        System.out.println("1) Ver catálogo");
        System.out.println("2) Agregar producto al carrito");
        System.out.println("3) Quitar producto del carrito");
        System.out.println("4) Aplicar código de descuento a ÍTEM");
        System.out.println("5) Aplicar código de descuento a PEDIDO");
        System.out.println("6) Ver carrito");
        System.out.println("7) Finalizar compra");
        System.out.println("8) Ayuda / ver códigos");
        System.out.println("9) Salir");
    }

    private void verCatalogo() {
        productListView.render(productController.list());
    }

    private void agregarProducto() {
        String sku = input.leerSku("SKU", productController);
        int qty = input.leerCantidad("Cantidad");
        try {
            Product p = productController.findBySku(sku);
            cartController.addProduct(p, qty);
            System.out.println("Producto agregado.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void quitarProducto() {
        if (order.getItems().isEmpty()) {
            System.out.println("Carrito vacío.");
            return;
        }
        String sku = input.leerSku("SKU a quitar", productController);
        boolean exists = order.getItems().stream()
                .anyMatch(li -> li.getProduct().getSku().equalsIgnoreCase(sku));
        if (!exists) {
            System.out.println("Ese SKU no está en el carrito.");
            return;
        }
        cartController.removeProduct(sku);
        System.out.println("Producto quitado.");
    }

    private void aplicarDescuentoItem() {
        if (order.getItems().isEmpty()) {
            System.out.println("Carrito vacío.");
            return;
        }
        String sku = input.leerSku("SKU del ítem", productController);
        boolean exists = order.getItems().stream()
                .anyMatch(li -> li.getProduct().getSku().equalsIgnoreCase(sku));
        if (!exists) {
            System.out.println("Ese SKU no está en el carrito.");
            return;
        }
        String code = input.leerCodigo("Código", dm);
        try {
            cartController.addDiscountToItem(sku, code);
            System.out.println("Código aplicado al ítem.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void aplicarDescuentoPedido() {
        String code = input.leerCodigo("Código", dm);
        cartController.addOrderLevelDiscount(code);
        System.out.println("Código aplicado al pedido.");
    }

    private void verCarrito() {
        cartView.render(order);
    }

    private void finalizarCompra() {
        if (order.getItems().isEmpty()) {
            System.out.println("No hay productos en el carrito.");
            return;
        }
        if (!input.confirmar("¿Confirmar compra?")) return;
        receiptPrinter.print(order);
        limpiarCarrito();
    }

    private void limpiarCarrito() {
        List<Order.LineItem> copia = new ArrayList<>(order.getItems());
        for (Order.LineItem li : copia) {
            cartController.removeProduct(li.getProduct().getSku());
        }
        order.getOrderLevelDiscountCodes().clear();
    }

    private void mostrarAyuda() {
        System.out.println("\nCódigos disponibles:");
        for (String code : dm.getRegisteredCodes()) {
            discountView.renderPipeline(code, discountController.getPipeline(code));
        }
    }
}
