package com.dqr.exps1s3.pricing;

import java.math.BigDecimal;
import static com.dqr.exps1s3.util.Money.dinero;

public final class PercentDiscountDecorator extends Decorator {
    private final BigDecimal percent; // 0.10 => 10%

    public PercentDiscountDecorator(Component inner, BigDecimal percent) {
        super(inner);
        if (percent.compareTo(BigDecimal.ZERO) < 0 || percent.compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("percent entre 0 y 1");
        this.percent = percent;
    }

    @Override public BigDecimal getTotal() {
        BigDecimal base = inner.getTotal();
        BigDecimal res = base.multiply(BigDecimal.ONE.subtract(percent));
        return dinero(res.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : res);
    }

    @Override public String getDescription() {
        return inner.getDescription()+" | -"+percent.multiply(BigDecimal.valueOf(100)).setScale(0)+"%";
    }
}
