package com.hakan.spinjection.command.exceptions;

import javax.annotation.Nonnull;

/**
 * Thrown when a filter is not passed.
 */
public class FilterNotPassedException extends RuntimeException {

    /**
     * Constructor of {@link FilterNotPassedException}
     *
     * @param message the message that will be sent to the user
     */
    public FilterNotPassedException(@Nonnull String message) {
        super(message);
    }
}