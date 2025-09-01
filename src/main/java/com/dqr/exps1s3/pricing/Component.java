package com.dqr.exps1s3.pricing;

import java.math.BigDecimal;

public interface Component {
    BigDecimal getSubtotal();
    BigDecimal getTotal();
    String getDescription();
    String getCategory();
}
