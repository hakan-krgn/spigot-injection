package com.hakan.injection.command.exceptions;

public class InsufficientPermissionException extends RuntimeException {

    public InsufficientPermissionException(String message) {
        super(message);
    }
}
