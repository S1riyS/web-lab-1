package ru.s1riys.web.lab1.exceptions;

public class MissingParametersException extends Exception {
    public MissingParametersException() {
        super();
    }

    public MissingParametersException(String message) {
        super(message);
    }

    public MissingParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingParametersException(Throwable cause) {
        super(cause);
    }
}
