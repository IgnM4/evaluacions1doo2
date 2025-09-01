package com.dqr.exps1s3.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money {
    private Money(){}

    /** CLP sin decimales (ajusta scale a 2 si usas USD/EUR). */
    public static BigDecimal dinero(BigDecimal x) {
        return x.setScale(0, RoundingMode.HALF_UP);
    }

    /** Trunca string con “…” si supera n. */
    public static String trunc(String s, int n) {
        if (s == null) return "";
        if (s.length() <= n) return s;
        return s.substring(0, Math.max(0, n-1)) + "…";
    }
}
