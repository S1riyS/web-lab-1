package ru.s1riys.web.lab1;

import java.math.BigDecimal;
import java.util.List;

public class Validator {
    private final BigDecimal x;
    private final String y;
    private final BigDecimal r;
    public Validator(String x, String y, String r) {
        this.x = new BigDecimal(x);
        this.y = y;
        this.r = new BigDecimal(r);
    }
    private final List<String> ALLOWED_Y_VALUES = List.of("-2", "-1.5", "-1", "-0.5", "0", "0.5", "1", "1.5", "2");
    private Boolean checkX() {
        Boolean geNegative5 = new BigDecimal("-5").compareTo(this.x) <= 0;
        Boolean lePositive3 = this.x.compareTo(new BigDecimal("3")) <= 0;
        return geNegative5 && lePositive3;
    }
    private Boolean checkY() {
        Double __ = Double.parseDouble(this.y); // In order to check if Y in number
        return ALLOWED_Y_VALUES.contains(this.y);
    }
    private Boolean checkR() {
        Boolean gePositive1 = new BigDecimal("1").compareTo(this.r) <= 0;
        Boolean lePositive4 = this.r.compareTo(new BigDecimal("4")) <= 0;
        return gePositive1 && lePositive4;
    }

    public Boolean check() {
        return checkX() && checkY() && checkR();
    }
}
