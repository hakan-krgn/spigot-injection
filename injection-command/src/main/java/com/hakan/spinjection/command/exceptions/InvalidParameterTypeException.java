package com.hakan.spinjection.command.exceptions;

import javax.annotation.Nonnull;

/**
 * Thrown when the command parameter type is invalid.
 * For example, if the command parameter type is Integer,
 * but the user provides a Character instead.
 */
public class InvalidParameterTypeException extends RuntimeException {

    /**
     * Constructor of {@link InvalidParameterTypeException}
     *
     * @param message the message that will be sent
     */
    public InvalidParameterTypeException(@Nonnull String message) {
        super(message);
    }
}
