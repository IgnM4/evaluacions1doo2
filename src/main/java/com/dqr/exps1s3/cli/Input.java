package com.dqr.exps1s3.cli;

import com.dqr.exps1s3.controller.ProductController;
import com.dqr.exps1s3.pricing.DiscountManager;
import java.util.Scanner;

/**
 * Utilidades de lectura desde consola con validación básica.
 */
public final class Input {
    private final Scanner sc = new Scanner(System.in);

    /** Lee un entero cualquiera, reintentando ante errores. */
    public int leerIntSeguro(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Ingresa un número.");
                continue;
            }
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }

    /** Lee una cantidad (>0). */
    public int leerCantidad(String prompt) {
        while (true) {
            int n = leerIntSeguro(prompt);
            if (n > 0) return n;
            System.out.println("La cantidad debe ser mayor a 0.");
        }
    }

    /** Lee SKU existente en catálogo. */
    public String leerSku(String prompt, ProductController pc) {
        while (true) {
            System.out.print(prompt + ": ");
            String sku = sc.nextLine().trim();
            if (sku.isEmpty()) {
                System.out.println("SKU vacío.");
                continue;
            }
            try {
                pc.findBySku(sku); // valida existencia
                return sku.toUpperCase();
            } catch (IllegalArgumentException e) {
                System.out.println("SKU inexistente.");
            }
        }
    }

    /** Lee código de descuento registrado. */
    public String leerCodigo(String prompt, DiscountManager dm) {
        while (true) {
            System.out.print(prompt + ": ");
            String code = sc.nextLine().trim();
            if (code.isEmpty()) {
                System.out.println("Código vacío.");
                continue;
            }
            if (dm.hasCode(code)) {
                return code.toUpperCase();
            }
            System.out.println("Código desconocido.");
        }
    }

    /** Pregunta sí/no. */
    public boolean confirmar(String prompt) {
        while (true) {
            System.out.print(prompt + " (s/n): ");
            String line = sc.nextLine().trim().toLowerCase();
            if (line.equals("s") || line.equals("si")) return true;
            if (line.equals("n") || line.equals("no")) return false;
            System.out.println("Responde s o n.");
        }
    }
}
