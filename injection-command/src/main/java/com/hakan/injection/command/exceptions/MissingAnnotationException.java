package com.hakan.injection.command.exceptions;

public class MissingAnnotationException extends RuntimeException {

    public MissingAnnotationException(String message) {
        super(message);
    }
}
