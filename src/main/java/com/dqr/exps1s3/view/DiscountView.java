package com.dqr.exps1s3.view;

import com.dqr.exps1s3.pricing.DiscountCommand;
import java.util.List;

public final class DiscountView {
    public void renderPipeline(String code, List<DiscountCommand> cmds) {
        System.out.println("\n? Código '"+code+"' ? pipeline de descuentos ?");
        if (cmds.isEmpty()) {
            System.out.println("  (sin comandos registrados)");
            return;
        }
        for (int i = 0; i < cmds.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + cmds.get(i).name());
        }
    }
}
