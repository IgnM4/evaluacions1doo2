package com.dqr.exps1s3.pricing;

import java.math.BigDecimal;

public final class GlobalPercentDiscountCommand implements DiscountCommand {
    private final BigDecimal percent;
    public GlobalPercentDiscountCommand(BigDecimal percent) { this.percent = percent; }

    @Override public Component Ejecutar(Component base) {
        return new PercentDiscountDecorator(base, percent);
    }
    @Override public String name() {
        return "Global -"+percent.multiply(BigDecimal.valueOf(100)).setScale(0)+"%";
    }
}
