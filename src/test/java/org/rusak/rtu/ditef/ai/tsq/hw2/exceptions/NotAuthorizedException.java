package org.rusak.rtu.ditef.ai.tsq.hw2.exceptions;

/**
 * Exception thrown when the user is not authorized to perform the action.
 */
public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
