package com.hakan.spinjection.command.exceptions;

import javax.annotation.Nonnull;

/**
 * Thrown when a command method does not have the
 * required annotation to be executed.
 */
public class MissingAnnotationException extends RuntimeException {

    /**
     * Constructor of {@link MissingAnnotationException}
     *
     * @param message the message that will be sent
     */
    public MissingAnnotationException(@Nonnull String message) {
        super(message);
    }
}
