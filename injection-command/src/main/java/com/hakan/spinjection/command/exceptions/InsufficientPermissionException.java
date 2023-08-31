package com.hakan.spinjection.command.exceptions;

import javax.annotation.Nonnull;

/**
 * Thrown when a user does not have the
 * required permission to execute a command.
 */
public class InsufficientPermissionException extends RuntimeException {

	/**
	 * Constructor of {@link InsufficientPermissionException}
	 *
	 * @param message the message that will be sent to the user
	 */
	public InsufficientPermissionException(@Nonnull String message) {
		super(message);
	}
}
