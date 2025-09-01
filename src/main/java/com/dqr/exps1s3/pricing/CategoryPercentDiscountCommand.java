package com.dqr.exps1s3.pricing;

import java.math.BigDecimal;

public final class CategoryPercentDiscountCommand implements DiscountCommand {
    private final String category;
    private final BigDecimal percent;

    public CategoryPercentDiscountCommand(String category, BigDecimal percent) {
        this.category = category;
        this.percent = percent;
    }

    @Override public Component Ejecutar(Component base) {
        return new CategoryPercentDiscountDecorator(base, category, percent);
    }
    @Override public String name() {
        return "Cat("+category+") -"+percent.multiply(BigDecimal.valueOf(100)).setScale(0)+"%";
    }
}
