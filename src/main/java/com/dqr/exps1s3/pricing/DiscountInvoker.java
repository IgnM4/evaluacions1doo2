package com.dqr.exps1s3.pricing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class DiscountInvoker {
    private final List<DiscountCommand> pipeline = new ArrayList<>();

    public DiscountInvoker add(DiscountCommand cmd) {
        pipeline.add(Objects.requireNonNull(cmd));
        return this;
    }

    public Component run(Component base) {
        Component current = base;
        for (DiscountCommand c : pipeline) current = c.Ejecutar(current);
        return current;
    }

    public List<DiscountCommand> getPipeline() {
        return Collections.unmodifiableList(pipeline);
    }

    public void clear() { pipeline.clear(); }
}
