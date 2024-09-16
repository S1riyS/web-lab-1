package ru.s1riys.web.lab1;

public class Checker {
    private static Boolean inTriangle(Float x, Float y, Float r) {
        return x >= 0 && y >= 0 && y <= -2 * x + r;
    }

    private static Boolean inCircle(Float x, Float y, Float r) {
        double distanceFromOrigin = Math.sqrt(x * x + y * y);
        return x <= 0 && y >= 0 && distanceFromOrigin <= r;
    }

    private static Boolean inSquare(Float x, Float y, Float r) {
        return (-r <= x && x <= 0) && (-r <= y && y <= 0);
    }

    public static Boolean hit(Float x, Float y, Float r) {
        return inTriangle(x, y, r) || inCircle(x, y, r) || inSquare(x, y, r);
    }
}
