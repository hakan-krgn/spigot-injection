package com.hakan.spinjection.command.exceptions;

import javax.annotation.Nonnull;

/**
 * Thrown when the command arguments length is invalid.
 * For example, if the command requires 2 arguments, but the user only provides 1.
 */
public class InvalidArgsLengthException extends RuntimeException {

	/**
	 * Constructor of {@link InvalidArgsLengthException}
	 *
	 * @param message the message that will be sent
	 */
	public InvalidArgsLengthException(@Nonnull String message) {
		super(message);
	}
}
