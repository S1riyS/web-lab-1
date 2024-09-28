package ru.s1riys.web.lab1.network;

public enum HttpStatusCode {
    OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    INTERNAL_ERROR(500);

    private final int value;

    HttpStatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
