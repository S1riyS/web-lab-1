package ru.s1riys.web.lab1;

public class HitChecker {
    private final Double x;
    private final Double y;
    private final Double r;
    
    public HitChecker(String x, String y, String r) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.r = Double.parseDouble(r);
    }

    private Boolean inTriangle() {
        return x >= 0 && y >= 0 && y <= -2 * x + r;
    }

    private Boolean inCircle() {
        double distanceFromOrigin = Math.sqrt(x * x + y * y);
        return x <= 0 && y >= 0 && distanceFromOrigin <= r;
    }

    private Boolean inSquare() {
        return (-r <= x && x <= 0) && (-r <= y && y <= 0);
    }

    public Boolean getResult() {
        return inTriangle() || inCircle() || inSquare();
    }
}
