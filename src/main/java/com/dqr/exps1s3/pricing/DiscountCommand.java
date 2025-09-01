package com.dqr.exps1s3.pricing;

public interface DiscountCommand {
    Component Ejecutar(Component base);
    String name();
}
