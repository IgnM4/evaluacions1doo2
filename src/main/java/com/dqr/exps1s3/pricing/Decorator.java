package com.dqr.exps1s3.pricing;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Decorator implements Component {
    protected final Component inner;
    protected Decorator(Component inner) { this.inner = Objects.requireNonNull(inner); }
    @Override public BigDecimal getSubtotal() { return inner.getSubtotal(); }
    @Override public String getDescription()  { return inner.getDescription(); }
    @Override public String getCategory()     { return inner.getCategory(); }
}
