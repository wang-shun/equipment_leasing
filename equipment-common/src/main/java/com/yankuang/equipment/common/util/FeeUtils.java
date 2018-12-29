package com.yankuang.equipment.common.util;

import java.math.BigDecimal;

public class FeeUtils {

    public static double scale(double fee, int sc) {
        BigDecimal b = BigDecimal.valueOf(fee);
        return b.setScale(sc,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
