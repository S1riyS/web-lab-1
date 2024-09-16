package ru.s1riys.web.lab1;

import java.util.List;

public class Validator {
    private static final List<Float> ALLOWED_Y_VALUES = List.of(-2f, -1.5f, -1f, -0.5f, 0f, 0.5f, 1f, 1.5f, 2f);
    public static Boolean validateX (Float x) {
        return -5 <= x && x <= 3;
    }
    public static Boolean validateY (Float y) {
        return ALLOWED_Y_VALUES.contains(y);
    }
    public static Boolean validateR (Float r) {
        return 1 <= r && r <= 4;
    }

    public static Boolean validateAll(Float x, Float y, Float r) {
        return validateX(x) && validateY(y) && validateR(r);
    }
}
