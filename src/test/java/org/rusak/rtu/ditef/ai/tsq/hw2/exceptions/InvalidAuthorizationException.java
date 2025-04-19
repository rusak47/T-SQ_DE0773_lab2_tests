package org.rusak.rtu.ditef.ai.tsq.hw2.exceptions;

/**
 * Exception thrown when the user failed to authorize due to invalid credentials.
 */
public class InvalidAuthorizationException extends NotAuthorizedException {
    public InvalidAuthorizationException(String message) {
        super(message);
    }

    public InvalidAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
