package org.example.aprocmd.exception.command;

public class CreateDtoException extends RuntimeException{

    public CreateDtoException() {
        super();
    }

    public CreateDtoException(String message) {
        super(message);
    }
}
