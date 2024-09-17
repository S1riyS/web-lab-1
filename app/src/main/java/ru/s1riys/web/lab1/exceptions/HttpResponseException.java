package ru.s1riys.web.lab1.exceptions;

public class HttpResponseException extends Exception {
    public HttpResponseException() {
        super();
    }

    public HttpResponseException(String message) {
        super(message);
    }

    public HttpResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpResponseException(Throwable cause) {
        super(cause);
    }
}
