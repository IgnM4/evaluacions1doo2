package com.dqr.exps1s3.pricing;

import java.math.BigDecimal;
import java.util.Objects;
import static com.dqr.exps1s3.util.Money.dinero;

public final class CategoryPercentDiscountDecorator extends Decorator {
    private final String category;
    private final BigDecimal percent;

    public CategoryPercentDiscountDecorator(Component inner, String category, BigDecimal percent) {
        super(inner);
        if (percent.compareTo(BigDecimal.ZERO) < 0 || percent.compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("percent entre 0 y 1");
        this.category = Objects.requireNonNull(category);
        this.percent  = percent;
    }

    @Override public BigDecimal getTotal() {
        BigDecimal base = inner.getTotal();
        if (inner.getCategory().equalsIgnoreCase(category)) {
            BigDecimal res = base.multiply(BigDecimal.ONE.subtract(percent));
            return dinero(res.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : res);
        }
        return base;
    }

    @Override public String getDescription() {
        return inner.getDescription()+" | -"+percent.multiply(BigDecimal.valueOf(100)).setScale(0)+"%("+category+")";
    }
}
