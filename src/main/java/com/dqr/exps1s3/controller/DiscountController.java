package com.dqr.exps1s3.controller;

import com.dqr.exps1s3.pricing.*;
import java.math.BigDecimal;
import java.util.List;

public final class DiscountController {
    private final DiscountManager dm = DiscountManager.getInstance();

    public void registerSimpleCodes() {
        dm.registerCode("SALE10", List.of(new GlobalPercentDiscountCommand(new BigDecimal("0.10"))));
        dm.registerCode("TOP20",  List.of(new CategoryPercentDiscountCommand("TOP", new BigDecimal("0.20"))));
        dm.registerCode("COMBO10", List.of(
                new GlobalPercentDiscountCommand(new BigDecimal("0.10")),
                new CategoryPercentDiscountCommand("TOP", new BigDecimal("0.20"))
        ));
    }

    public java.util.List<DiscountCommand> getPipeline(String code) {
        return dm.getCommands(code);
    }
}
