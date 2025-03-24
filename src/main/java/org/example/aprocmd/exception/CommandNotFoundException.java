package org.example.aprocmd.exception;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException() {
        super();
    }

    public CommandNotFoundException(String message) {
        super(message);
    }
}
