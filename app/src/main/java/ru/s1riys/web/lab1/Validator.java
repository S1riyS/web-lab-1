package ru.s1riys.web.lab1;

import java.util.List;

public class Validator {
    private static final List<Double> ALLOWED_Y_VALUES = List.of(-2d, -1.5d, -1d, -0.5d, 0d, 0.5d, 1d, 1.5d, 2d);
    public static Boolean validateX (Double x) {
        return -5 <= x && x <= 3;
    }
    public static Boolean validateY (Double y) {
        return ALLOWED_Y_VALUES.contains(y);
    }
    public static Boolean validateR (Double r) {
        return 1 <= r && r <= 4;
    }

    public static Boolean validateAll(Double x, Double y, Double r) {
        return validateX(x) && validateY(y) && validateR(r);
    }
}
