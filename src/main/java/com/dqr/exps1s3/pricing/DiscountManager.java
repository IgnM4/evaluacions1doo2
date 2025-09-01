package com.dqr.exps1s3.pricing;

import com.dqr.exps1s3.model.Order;
import java.math.BigDecimal;
import java.util.*;
import static com.dqr.exps1s3.util.Money.dinero;

public final class DiscountManager {
    private final Map<String, List<DiscountCommand>> registry = new HashMap<>();

    private DiscountManager(){}

    private static class Holder {
        private static final DiscountManager INSTANCE = new DiscountManager();
    }

    public static DiscountManager getInstance() { return Holder.INSTANCE; }

    public void registerCode(String code, List<DiscountCommand> commands) {
        registry.put(code.toUpperCase(), new ArrayList<>(commands));
    }

    public boolean hasCode(String code) {
        return registry.containsKey(code.toUpperCase());
    }

    public List<DiscountCommand> getCommands(String code) {
        return registry.getOrDefault(code.toUpperCase(), List.of());
    }

    // CHANGE: exponer códigos registrados para la CLI
    public java.util.Set<String> getRegisteredCodes() { // CHANGE
        return java.util.Collections.unmodifiableSet(registry.keySet()); // CHANGE
    } // CHANGE

    public Component applyCodeToComponent(String code, Component base) {
        DiscountInvoker inv = new DiscountInvoker();
        getCommands(code).forEach(inv::add);
        return inv.run(base);
    }

    public BigDecimal totalForLineItem(Order.LineItem item) {
        Component c = new LineItemComponent(item);
        Component decorated = c;
        for (String code : item.getDiscountCodes()) {
            decorated = applyCodeToComponent(code, decorated);
        }
        return dinero(decorated.getTotal());
    }

    public BigDecimal totalForOrder(Order order) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Order.LineItem li : order.getItems()) {
            sum = sum.add(totalForLineItem(li));
        }
        final BigDecimal sumFinal = sum; // para clase anónima (effectively final)
        Component whole = new Component() {
            @Override public BigDecimal getSubtotal() { return sumFinal; }
            @Override public BigDecimal getTotal()    { return sumFinal; }
            @Override public String getDescription()  { return "ORDER_TOTAL"; }
            @Override public String getCategory()     { return "ORDER"; }
        };
        Component decorated = whole;
        for (String code : order.getOrderLevelDiscountCodes()) {
            decorated = applyCodeToComponent(code, decorated);
        }
        return dinero(decorated.getTotal());
    }
}
