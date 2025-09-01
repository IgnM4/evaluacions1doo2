package com.dqr.exps1s3.pricing;

import com.dqr.exps1s3.model.Order;
import java.math.BigDecimal;
import java.util.Objects;
import static com.dqr.exps1s3.util.Money.dinero;

public final class LineItemComponent implements Component {
    private final Order.LineItem item;

    public LineItemComponent(Order.LineItem item) { this.item = Objects.requireNonNull(item); }

    @Override public BigDecimal getSubtotal() { return dinero(item.subtotal()); }
    @Override public BigDecimal getTotal()    { return getSubtotal(); }
    @Override public String getDescription()  { return item.getProduct().getName()+" x"+item.getQuantity(); }
    @Override public String getCategory()     { return item.getProduct().getCategory(); }
}
