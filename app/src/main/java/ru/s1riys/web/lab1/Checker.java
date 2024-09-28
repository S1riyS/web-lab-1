package ru.s1riys.web.lab1;

public class Checker {
    private static Boolean inTriangle(Double x, Double y, Double r) {
        return x >= 0 && y >= 0 && y <= -2 * x + r;
    }

    private static Boolean inCircle(Double x, Double y, Double r) {
        double distanceFromOrigin = Math.sqrt(x * x + y * y);
        return x <= 0 && y >= 0 && distanceFromOrigin <= r;
    }

    private static Boolean inSquare(Double x, Double y, Double r) {
        return (-r <= x && x <= 0) && (-r <= y && y <= 0);
    }

    public static Boolean hit(Double x, Double y, Double r) {
        return inTriangle(x, y, r) || inCircle(x, y, r) || inSquare(x, y, r);
    }
}
