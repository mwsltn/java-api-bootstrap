package net.tretin.api.core;

public class ApiRuntimeException extends RuntimeException {
    public ApiRuntimeException(String message) {
        super(message);
    }

    public ApiRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
